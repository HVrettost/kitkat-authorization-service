package kitkat.auth.service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kitkat.auth.dao.RefreshTokenWhitelistDao;
import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.exception.error.AuthError;
import kitkat.auth.model.dto.AuthTokenDto;
import kitkat.auth.util.HeaderUtils;
import kitkat.auth.util.JwtUtils;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final RefreshTokenWhitelistDao refreshTokenWhitelistDao;
    private final JwtUtils jwtUtils;
    private final AuthRoleService authRoleService;

    public AuthTokenServiceImpl(RefreshTokenWhitelistDao refreshTokenWhitelistDao,
                                JwtUtils jwtUtils,
                                AuthRoleService authRoleService) {
        this.refreshTokenWhitelistDao = refreshTokenWhitelistDao;
        this.jwtUtils = jwtUtils;
        this.authRoleService = authRoleService;
    }

    @Override
    public void invalidateRefreshTokenIfExistsAndSaveNew(String refreshToken, String username, String userAgent) {
        if (refreshTokenWhitelistDao.countTokensByUsernameAndUserAgent(username, userAgent) > 0) {
            refreshTokenWhitelistDao.invalidateRefreshToken(username, userAgent);
        }

        refreshTokenWhitelistDao.saveRefreshToken(refreshToken, username, userAgent);
    }

    @Override
    @Transactional
    public AuthTokenDto updateAccessToken(HttpServletRequest httpServletRequest) {
        String refreshToken = HeaderUtils.extractAuthorizationHeader(httpServletRequest);
        String username = jwtUtils.extractSubjectClaim(refreshToken);
        String permissions = getUserPermissions(username);
        String accessToken = jwtUtils.generateAccessToken(username, permissions);

        return new AuthTokenDto(username, accessToken, refreshToken);
    }

    @Override
    public AuthTokenDto createAuthTokenDto(String username, String permissions, String userAgent) {
        AuthTokenDto authTokenDto = new AuthTokenDto();
        authTokenDto.setUsername(username);
        authTokenDto.setAccessToken(jwtUtils.generateAccessToken(username, permissions));
        authTokenDto.setRefreshToken(jwtUtils.generateRefreshToken(username));

        return authTokenDto;
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
