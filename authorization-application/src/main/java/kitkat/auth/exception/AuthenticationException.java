package kitkat.auth.exception;

import kitkat.auth.enumeration.ErrorDetails;
import kitkat.auth.exception.error.AuthenticationError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthenticationException extends CoreException {

    private final ErrorDetails errorDetails;
    private final HttpStatus httpStatus;
    private final String description;

    public AuthenticationException(AuthenticationError authenticationError) {
        super(authenticationError.getMessage());
        this.description = authenticationError.getDescription();
        this.httpStatus = authenticationError.getHttpStatus();
        this.errorDetails = new ErrorDetails(authenticationError.getErrorCode(), authenticationError.getMessage());
    }

    public AuthenticationException(ErrorDetails errorDetails, HttpStatus httpStatus) {
        super(errorDetails.getMessage());
        this.errorDetails = errorDetails;
        this.httpStatus = httpStatus;
        this.description = errorDetails.getMessage();
    }
}
