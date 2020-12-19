package kitkat.auth.validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import kitkat.auth.enumeration.TokenType;
import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.exception.error.AuthError;
import org.springframework.stereotype.Component;

@Component
public class TokenCookieValidator implements Validator<String> {

    @Override
    public void validate(String cookie) {
        if (cookie == null || "".equals(cookie)) {
            throw new AuthorizationException(AuthError.TOKEN_COULD_NOT_BE_EXTRACTED);
        }

        List<String> cookieParts = Arrays.stream(cookie.split("; "))
                .collect(Collectors.toList());

        if (!isCookieConsistsOfTwoParts(cookieParts)
                || !accessTokenExists(cookieParts)
                || !refreshTokenExists(cookieParts)) {
            throw new AuthorizationException(AuthError.TOKEN_COULD_NOT_BE_EXTRACTED);
        }
    }

    private boolean isCookieConsistsOfTwoParts(List<String> cookieParts) {
        return cookieParts.size() == 2;
    }

    private boolean accessTokenExists(List<String> cookieParts) {
        return cookieParts.stream().anyMatch(part -> part.contains(TokenType.ACCESS.getValue()));
    }

    private boolean refreshTokenExists(List<String> cookieParts) {
        return cookieParts.stream().anyMatch(part -> part.contains(TokenType.REFRESH.getValue()));
    }
}