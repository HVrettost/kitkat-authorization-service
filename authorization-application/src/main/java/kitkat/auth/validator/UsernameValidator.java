package kitkat.auth.validator;

import kitkat.auth.exception.AuthenticationException;
import kitkat.auth.exception.error.AuthenticationError;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UsernameValidator implements Validator<String> {

    private static final int USERNAME_ACCEPTABLE_LENGTH = 10;

    @Override
    public void validate(String username) {
        if (StringUtils.isEmpty(username) || username.length() > USERNAME_ACCEPTABLE_LENGTH) {
            throw new AuthenticationException(AuthenticationError.BAD_CREDENTIALS);
        }
    }
}
