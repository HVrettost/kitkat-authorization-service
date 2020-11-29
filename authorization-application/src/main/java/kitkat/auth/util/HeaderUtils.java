package kitkat.auth.util;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.exception.error.AuthError;
import org.springframework.http.HttpHeaders;

public class HeaderUtils {

    public static String extractUserAgent(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.USER_AGENT))
                .orElseThrow(() -> new AuthorizationException(AuthError.USER_AGENT_NOT_FOUND));
    }

    public static String extractAuthorizationHeader(HttpServletRequest request) {
        String token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .orElseThrow(() -> new AuthorizationException(AuthError.TOKEN_COULD_NOT_BE_EXTRACTED));

        return token.replace("Bearer ", "");
    }
}
