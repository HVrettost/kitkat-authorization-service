package kitkat.auth.service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import kitkat.auth.dao.AuthRoleToPermissionsDao;
import kitkat.auth.dao.AuthRoleToUsernameDao;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import kitkat.auth.dao.RefreshTokenWhitelistDao;
import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.exception.error.AuthError;
import kitkat.auth.jwt.helper.JwtGenerator;
import kitkat.auth.jwt.util.JwtClaimUtils;
import kitkat.auth.util.CookieUtils;
import kitkat.auth.util.HeaderUtils;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final RefreshTokenWhitelistDao refreshTokenWhitelistDao;
    private final JwtClaimUtils jwtClaimUtils;
    private final JwtGenerator jwtGenerator;
    private final AuthRoleToUsernameDao authRoleToUsernameDao;
    private final AuthRoleToPermissionsDao authRoleToPermissionsDao;
    private final CookieUtils cookieUtils;
    private final HeaderUtils headerUtils;

    public AuthTokenServiceImpl(RefreshTokenWhitelistDao refreshTokenWhitelistDao,
                                JwtClaimUtils jwtClaimUtils,
                                JwtGenerator jwtGenerator,
                                AuthRoleToUsernameDao authRoleToUsernameDao,
                                AuthRoleToPermissionsDao authRoleToPermissionsDao,
                                CookieUtils cookieUtils,
                                HeaderUtils headerUtils) {
        this.refreshTokenWhitelistDao = refreshTokenWhitelistDao;
        this.jwtClaimUtils = jwtClaimUtils;
        this.jwtGenerator = jwtGenerator;
        this.authRoleToUsernameDao = authRoleToUsernameDao;
        this.authRoleToPermissionsDao = authRoleToPermissionsDao;
        this.cookieUtils = cookieUtils;
        this.headerUtils = headerUtils;
    }

    @Override
    @Transactional
    public HttpHeaders createCookieHeadersForAuthorization(HttpServletRequest httpServletRequest, String username) {
        String userAgent = headerUtils.extractUserAgent(httpServletRequest);
        String permissions = getUserPermissions(username);
        String accessToken = jwtGenerator.generateAccessToken(username, permissions);
        String refreshToken = jwtGenerator.generateRefreshToken(username);

        invalidateRefreshTokenIfExistsAndSaveNew(refreshToken, username, userAgent);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtils.createAccessTokenCookie(accessToken));
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtils.createRefreshTokenCookie(refreshToken));

        return httpHeaders;
    }

    private void invalidateRefreshTokenIfExistsAndSaveNew(String refreshToken, String username, String userAgent) {
        if (refreshTokenWhitelistDao.isRefreshTokenExistsByUsernameAndUserAgent(username, userAgent)) {
            refreshTokenWhitelistDao.invalidateRefreshToken(username, userAgent);
        }

        refreshTokenWhitelistDao.saveRefreshToken(refreshToken, username, userAgent);
    }

    @Override
    public HttpHeaders updateCookieHeaderForAccessToken(HttpServletRequest httpServletRequest) {
        String refreshToken = headerUtils.extractRefreshToken(httpServletRequest);
        String username = jwtClaimUtils.extractSubjectClaim(refreshToken);
        String permissions = getUserPermissions(username);
        String accessToken = jwtGenerator.generateAccessToken(username, permissions);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtils.createRefreshTokenCookie(refreshToken));
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtils.createAccessTokenCookie(accessToken));

        return httpHeaders;
    }

    private String getUserPermissions(String username) {
        String authRole = authRoleToUsernameDao.getAuthRoleByUsername(username).getRole();
        return authRoleToPermissionsDao.getPermissionsByAuthRole(authRole).getAuthorities();
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
