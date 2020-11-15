package kitkat.auth.util;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;

public class HeaderUtils {

    public static String extractUserAgent(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.USER_AGENT)).orElse("");
    }

    public static String extractAuthorizationHeader(HttpServletRequest request) {
        String token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)).orElse("");

        return token.replace("Bearer ", "");
    }
}
