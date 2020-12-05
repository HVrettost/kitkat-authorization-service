package kitkat.auth.util;

import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "accessToken";
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    private static final String COOKIE_PATH = "api/auth/token";
    //Make it parameterized for all cookie parameter

    public String createAccessTokenCookie(String accessToken) {
        return ACCESS_TOKEN_COOKIE_NAME + "=" + accessToken + "; Path=" + COOKIE_PATH + "; HttpOnly; Domain=localhost; SameSite=None; Secure";
    }

    public String createRefreshTokenCookie(String refreshToken) {
        return REFRESH_TOKEN_COOKIE_NAME + "=" + refreshToken + "; Path=" + COOKIE_PATH + "; HttpOnly; Domain=localhost; SameSite=None; Secure";
    }
}
