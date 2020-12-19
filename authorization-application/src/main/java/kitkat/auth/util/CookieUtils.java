package kitkat.auth.util;

import org.springframework.stereotype.Component;

import kitkat.auth.config.properties.CookieConfigProperties;
import org.springframework.util.StringUtils;

@Component
public class CookieUtils {

    private final CookieConfigProperties cookieConfigProperties;

    public CookieUtils(CookieConfigProperties cookieConfigProperties) {
        this.cookieConfigProperties = cookieConfigProperties;
    }

    public String createAccessTokenCookie(String accessToken) {
        return accessTokenFlag(accessToken)
                + pathFlag()
                + httpOnlyFlag()
                + secureFlag()
                + sameSiteFlag()
                + domainFlag();
    }

    public String createRefreshTokenCookie(String refreshToken) {
        return refreshTokenFlag(refreshToken)
                + pathFlag()
                + httpOnlyFlag()
                + secureFlag()
                + sameSiteFlag()
                + domainFlag();

    }

    private String accessTokenFlag(String accessToken) {
        return "accessToken=" + accessToken + "; ";
    }

    private String refreshTokenFlag(String refreshToken) {
        return "refreshToken=" + refreshToken + "; ";
    }

    private String pathFlag() {
        return "Path=" + cookieConfigProperties.getPath() + "; ";
    }

    private String httpOnlyFlag() {
        return Boolean.getBoolean(cookieConfigProperties.getHttpOnly())
                ? "HttpOnly; "
                : "";
    }

    private String sameSiteFlag() {
        return !StringUtils.isEmpty(cookieConfigProperties.getSameSite())
                ? "SameSite=" + cookieConfigProperties.getSameSite() + "; "
                : "";
    }

    private String secureFlag() {
        return Boolean.getBoolean(cookieConfigProperties.getSecure())
                ? "Secure; "
                : "";
    }

    //Domain flag will be always the last flag in order not to contain the semi colon as it is mandatory to exist.
    //Cookie last attribute should not contain semi colon. This is for not having any hard to conceive functionality and keep it simple
    private String domainFlag() {
        return "Domain=" + cookieConfigProperties.getDomain();
    }
}
