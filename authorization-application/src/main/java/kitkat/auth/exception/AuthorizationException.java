package kitkat.auth.exception;

import lombok.Getter;

import kitkat.auth.enumeration.ErrorDetails;
import kitkat.auth.exception.error.AuthorizationError;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorizationException extends CoreException {

    private final ErrorDetails errorDetails;
    private final HttpStatus httpStatus;
    private final String description;

    public AuthorizationException(AuthorizationError authorizationError) {
        super(authorizationError.getMessage());
        this.description = authorizationError.getDescription();
        this.httpStatus = authorizationError.getHttpStatus();
        this.errorDetails = new ErrorDetails(authorizationError.getErrorCode(), authorizationError.getMessage());
    }

    public AuthorizationException(ErrorDetails errorDetails, HttpStatus httpStatus) {
        super(errorDetails.getMessage());
        this.errorDetails = errorDetails;
        this.httpStatus = httpStatus;
        this.description = errorDetails.getMessage();
    }
}
