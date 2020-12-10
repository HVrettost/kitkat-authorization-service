package kitkat.auth.util;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.assertj.core.util.Lists;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import kitkat.auth.enumeration.TokenType;
import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.exception.error.AuthError;

@Component
public class HeaderUtils {

    public String extractUserAgent(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.USER_AGENT))
                .orElseThrow(() -> new AuthorizationException(AuthError.USER_AGENT_NOT_FOUND));
    }

    public String extractAccessToken(HttpServletRequest request) {
        return extractToken(request, TokenType.ACCESS.getValue());
    }

    public String extractRefreshToken(HttpServletRequest request) {
        return extractToken(request, TokenType.REFRESH.getValue());
    }

    private String extractToken(HttpServletRequest request, String tokenType) {
        String cookie = Optional.ofNullable(request.getHeader(HttpHeaders.COOKIE))
                .orElseThrow(() -> new AuthorizationException(AuthError.TOKEN_COULD_NOT_BE_EXTRACTED));

        String token = Arrays.stream(cookie.split("; "))
                             .filter(cookiePart -> cookiePart.contains(tokenType))
                             .findFirst()
                             .orElseThrow(() -> new AuthorizationException(AuthError.TOKEN_COULD_NOT_BE_EXTRACTED));

        return token.replace(tokenType + "=", "");
    }
}
