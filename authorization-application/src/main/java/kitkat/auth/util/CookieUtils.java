package kitkat.auth.util;

import javax.servlet.http.Cookie;

import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "accessToken";
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    private static final String COOKIE_PATH = "/auth/token";

    public Cookie createAccessTokenCookie(String cookieValue) {
        Cookie accessTokenCookie = new Cookie(ACCESS_TOKEN_COOKIE_NAME, cookieValue);
        accessTokenCookie.setPath(COOKIE_PATH);
        accessTokenCookie.setHttpOnly(true);

        return accessTokenCookie;
    }

    public Cookie createRefreshTokenCookie(String cookieValue) {
        Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, cookieValue);
        refreshTokenCookie.setPath(COOKIE_PATH);
        refreshTokenCookie.setHttpOnly(true);

        return refreshTokenCookie;
    }
}
