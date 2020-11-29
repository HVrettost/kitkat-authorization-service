package kitkat.auth.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthTokenService {

    void invalidateRefreshToken(HttpServletRequest httpServletRequest);

    void invalidateRefreshTokenIfExistsAndSaveNew(String refreshToken, String username, String userAgent);

    void updateAccessToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    String getUserPermissions(String username);

    void invalidateRefreshTokensByUsername(HttpServletRequest httpServletRequest);
}
