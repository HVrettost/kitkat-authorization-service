package kitkat.auth.service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import kitkat.auth.jwt.helper.JwtGenerator;
import kitkat.auth.jwt.util.JwtClaimUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import kitkat.auth.dao.RefreshTokenWhitelistDao;
import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.exception.error.AuthError;
import kitkat.auth.util.CookieUtils;
import kitkat.auth.util.HeaderUtils;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final RefreshTokenWhitelistDao refreshTokenWhitelistDao;
    private final JwtClaimUtils jwtClaimUtils;
    private final JwtGenerator jwtGenerator;
    private final AuthRoleService authRoleService;
    private final CookieUtils cookieUtils;
    private final HeaderUtils headerUtils;

    public AuthTokenServiceImpl(RefreshTokenWhitelistDao refreshTokenWhitelistDao,
                                JwtClaimUtils jwtClaimUtils,
                                JwtGenerator jwtGenerator,
                                AuthRoleService authRoleService,
                                CookieUtils cookieUtils,
                                HeaderUtils headerUtils) {
        this.refreshTokenWhitelistDao = refreshTokenWhitelistDao;
        this.jwtClaimUtils = jwtClaimUtils;
        this.jwtGenerator = jwtGenerator;
        this.authRoleService = authRoleService;
        this.cookieUtils = cookieUtils;
        this.headerUtils = headerUtils;
    }

    @Override
    @Transactional
    public HttpHeaders createCookieHeadersForAuthorization(HttpServletRequest httpServletRequest, String username) {
        HttpHeaders httpHeaders = new HttpHeaders();

        String userAgent = headerUtils.extractUserAgent(httpServletRequest);
        String permissions = getUserPermissions(username);
        String accessToken = jwtGenerator.generateAccessToken(username, permissions);
        String refreshToken = jwtGenerator.generateRefreshToken(username);

        invalidateRefreshTokenIfExistsAndSaveNew(refreshToken, username, userAgent);

        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtils.createAccessTokenCookie(accessToken));
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtils.createRefreshTokenCookie(refreshToken));

        return httpHeaders;
    }

    private void invalidateRefreshTokenIfExistsAndSaveNew(String refreshToken, String username, String userAgent) {
        if (refreshTokenWhitelistDao.countTokensByUsernameAndUserAgent(username, userAgent) > 0) {
            refreshTokenWhitelistDao.invalidateRefreshToken(username, userAgent);
        }

        refreshTokenWhitelistDao.saveRefreshToken(refreshToken, username, userAgent);
    }

    @Override
    public HttpHeaders updateCookieHeaderForAccessToken(HttpServletRequest httpServletRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();

        String refreshToken = headerUtils.extractRefreshToken(httpServletRequest);
        String username = jwtClaimUtils.extractSubjectClaim(refreshToken);
        String permissions = getUserPermissions(username);
        String accessToken = jwtGenerator.generateAccessToken(username, permissions);
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtils.createAccessTokenCookie(accessToken));

        return httpHeaders;
    }

    private String getUserPermissions(String username) {
        String authRole = authRoleService.getAuthRoleByUsername(username).getRole();
        return authRoleService.getPermissionsByAuthRole(authRole).getAuthorities();
    }


    @Override
    @Transactional
    public void invalidateRefreshToken(HttpServletRequest httpServletRequest) {
        String accessToken = headerUtils.extractAccessToken(httpServletRequest);
        String userAgent = headerUtils.extractUserAgent(httpServletRequest);
        String username = jwtClaimUtils.extractSubjectClaim(accessToken);

        if (refreshTokenWhitelistDao.isRefreshTokenExistsByUsernameAndUserAgent(username, userAgent)) {
            refreshTokenWhitelistDao.invalidateRefreshToken(username, userAgent);
        } else {
            throw new AuthorizationException(AuthError.REFRESH_TOKEN_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void invalidateRefreshTokensByUsername(HttpServletRequest httpServletRequest) {
        String accessToken = headerUtils.extractAccessToken(httpServletRequest);
        String username = jwtClaimUtils.extractSubjectClaim(accessToken);

        if (refreshTokenWhitelistDao.isRefreshTokenExistsByUsername(username)) {
            refreshTokenWhitelistDao.invalidateRefreshTokensByUsername(username);
        } else {
            throw new AuthorizationException(AuthError.REFRESH_TOKENS_NOT_FOUND);
        }
    }
}
