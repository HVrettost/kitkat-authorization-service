package kitkat.auth.service;

import kitkat.auth.model.dto.AuthTokenDto;

import javax.servlet.http.HttpServletRequest;

public interface AuthTokenService {

    void invalidateRefreshToken(HttpServletRequest httpServletRequest);

    void invalidateRefreshTokenIfExistsAndSaveNew(String refreshToken, String username, String userAgent);

    AuthTokenDto updateAccessToken(HttpServletRequest httpServletRequest);

    AuthTokenDto createAuthTokenDto(String username, String permissions, String userAgent);

    String getUserPermissions(String username);

    void invalidateRefreshTokensByUsername(HttpServletRequest httpServletRequest);
}
