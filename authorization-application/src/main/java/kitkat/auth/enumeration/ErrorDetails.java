package kitkat.auth.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorDetails {

    private final int errorCode;
    private final String message;
}
