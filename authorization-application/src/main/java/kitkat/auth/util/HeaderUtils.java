package kitkat.auth.util;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import kitkat.auth.validator.TokenCookieValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import kitkat.auth.enumeration.TokenType;
import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.exception.error.AuthorizationError;

@Component
public class HeaderUtils {

    private final TokenCookieValidator tokenCookieValidator;

    public HeaderUtils(TokenCookieValidator tokenCookieValidator) {
        this.tokenCookieValidator = tokenCookieValidator;
    }

    public String extractUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        if (userAgent == null || "".equals(userAgent)) {
            throw new AuthorizationException(AuthorizationError.USER_AGENT_NOT_FOUND);
        }

        return userAgent;
    }

    public String extractAccessToken(HttpServletRequest request) {
        return extractToken(request, TokenType.ACCESS.getValue());
    }

    public String extractRefreshToken(HttpServletRequest request) {
        return extractToken(request, TokenType.REFRESH.getValue());
    }

    private String extractToken(HttpServletRequest request, String tokenType) {
        String cookie = request.getHeader(HttpHeaders.COOKIE);
        tokenCookieValidator.validate(cookie);

        String token = Arrays.stream(cookie.split("; "))
                             .filter(cookiePart -> cookiePart.contains(tokenType))
                             .findFirst()
                             .get();


        return token.replace(tokenType + "=", "");
    }
}
