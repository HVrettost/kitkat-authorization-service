package kitkat.auth.exception;

import lombok.Getter;

import kitkat.auth.enumeration.ErrorDetails;
import kitkat.auth.exception.error.AuthError;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorizationException extends CoreException {

    private final ErrorDetails errorDetails;
    private final HttpStatus httpStatus;
    private final String description;

    public AuthorizationException(AuthError authError) {
        super(authError.getMessage());
        this.description = authError.getDescription();
        this.httpStatus = authError.getHttpStatus();
        this.errorDetails = new ErrorDetails(authError.getErrorCode(), authError.getMessage());
    }
}
