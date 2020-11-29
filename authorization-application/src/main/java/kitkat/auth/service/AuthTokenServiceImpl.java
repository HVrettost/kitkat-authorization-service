package kitkat.auth.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kitkat.auth.dao.RefreshTokenWhitelistDao;
import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.exception.error.AuthError;
import kitkat.auth.util.CookieUtils;
import kitkat.auth.util.HeaderUtils;
import kitkat.auth.util.JwtUtils;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final RefreshTokenWhitelistDao refreshTokenWhitelistDao;
    private final JwtUtils jwtUtils;
    private final AuthRoleService authRoleService;
    private final CookieUtils cookieUtils;

    public AuthTokenServiceImpl(RefreshTokenWhitelistDao refreshTokenWhitelistDao,
                                JwtUtils jwtUtils,
                                AuthRoleService authRoleService,
                                CookieUtils cookieUtils) {
        this.refreshTokenWhitelistDao = refreshTokenWhitelistDao;
        this.jwtUtils = jwtUtils;
        this.authRoleService = authRoleService;
        this.cookieUtils = cookieUtils;
    }

    @Override
    public void invalidateRefreshTokenIfExistsAndSaveNew(String refreshToken, String username, String userAgent) {
        if (refreshTokenWhitelistDao.countTokensByUsernameAndUserAgent(username, userAgent) > 0) {
            refreshTokenWhitelistDao.invalidateRefreshToken(username, userAgent);
        }

        refreshTokenWhitelistDao.saveRefreshToken(refreshToken, username, userAgent);
    }

    @Override
    public void updateAccessToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String refreshToken = HeaderUtils.extractAuthorizationHeader(httpServletRequest);
        String username = jwtUtils.extractSubjectClaim(refreshToken);
        String permissions = getUserPermissions(username);
        String accessToken = jwtUtils.generateAccessToken(username, permissions);
        httpServletResponse.addCookie(cookieUtils.createAccessTokenCookie(accessToken));
    }

    @Override
    public String getUserPermissions(String username) {
        String authRole = authRoleService.getAuthRoleByUsername(username).getRole();
        return authRoleService.getPermissionsByAuthRole(authRole).getAuthorities();
    }

    @Override
    @Transactional
    public void invalidateRefreshToken(HttpServletRequest httpServletRequest) {
        String accessToken = HeaderUtils.extractAuthorizationHeader(httpServletRequest);
        String userAgent = HeaderUtils.extractUserAgent(httpServletRequest);
        String username = jwtUtils.extractSubjectClaim(accessToken);

        if (refreshTokenWhitelistDao.isRefreshTokenExistsByUsernameAndUserAgent(username, userAgent)) {
            refreshTokenWhitelistDao.invalidateRefreshToken(username, userAgent);
        } else {
            throw new AuthorizationException(AuthError.REFRESH_TOKEN_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void invalidateRefreshTokensByUsername(HttpServletRequest httpServletRequest) {
        String accessToken = HeaderUtils.extractAuthorizationHeader(httpServletRequest);
        String username = jwtUtils.extractSubjectClaim(accessToken);

        if (refreshTokenWhitelistDao.isRefreshTokenExistsByUsername(username)) {
            refreshTokenWhitelistDao.invalidateRefreshTokensByUsername(username);
        } else {
            throw new AuthorizationException(AuthError.REFRESH_TOKENS_NOT_FOUND);
        }
    }
}
