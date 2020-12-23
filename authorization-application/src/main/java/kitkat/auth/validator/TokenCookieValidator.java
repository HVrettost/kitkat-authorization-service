package kitkat.auth.validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import kitkat.auth.enumeration.TokenType;
import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.exception.error.AuthorizationError;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TokenCookieValidator implements Validator<String> {

    @Override
    public void validate(String cookie) {
        if (StringUtils.isEmpty(cookie)) {
            throw new AuthorizationException(AuthorizationError.TOKEN_COULD_NOT_BE_EXTRACTED);
        }

        List<String> cookieParts = Arrays.stream(cookie.split("; "))
                .collect(Collectors.toList());

        if (!accessTokenExists(cookieParts) || !refreshTokenExists(cookieParts)) {
            throw new AuthorizationException(AuthorizationError.TOKEN_COULD_NOT_BE_EXTRACTED);
        }
    }

    private boolean accessTokenExists(List<String> cookieParts) {
        return cookieParts.stream().anyMatch(part -> part.contains(TokenType.ACCESS.getValue()));
    }

    private boolean refreshTokenExists(List<String> cookieParts) {
        return cookieParts.stream().anyMatch(part -> part.contains(TokenType.REFRESH.getValue()));
    }
}
