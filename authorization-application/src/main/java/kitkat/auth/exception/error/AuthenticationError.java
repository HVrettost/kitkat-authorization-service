package kitkat.auth.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthenticationError {

    BAD_CREDENTIALS(1002, "Username and password could not be verified", "", HttpStatus.UNAUTHORIZED),
    GENERIC_ERROR(1010, "Auth Generic Error", "Auth Generic Error", HttpStatus.BAD_REQUEST);

    private final int errorCode;
    private final String message;
    private final String description;
    private final HttpStatus httpStatus;
}
