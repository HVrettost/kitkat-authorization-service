package kitkat.auth.exception;

import lombok.Getter;

import kitkat.auth.enumeration.ErrorDetails;
import kitkat.auth.exception.error.AuthError;

@Getter
public class AuthorizationException extends CoreException {

    private final ErrorDetails errorDetails;
    private final String description;

    public AuthorizationException(AuthError authError) {
        super(authError.getMessage());
        this.description = authError.getDescription();
        this.errorDetails = new ErrorDetails(authError.getErrorCode(), authError.getMessage());
    }
}
