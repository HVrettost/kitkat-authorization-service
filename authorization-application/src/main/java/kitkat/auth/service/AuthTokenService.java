package kitkat.auth.service;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

public interface AuthTokenService {

    HttpHeaders createCookieHeadersForAuthorization(HttpServletRequest httpServletRequest, String username);

    HttpHeaders updateCookieHeaderForAccessToken(HttpServletRequest httpServletRequest);

    void invalidateRefreshToken(HttpServletRequest httpServletRequest);

    void invalidateRefreshTokensByUsername(HttpServletRequest httpServletRequest);
}
