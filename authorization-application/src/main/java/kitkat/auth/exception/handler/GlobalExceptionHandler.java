package kitkat.auth.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import kitkat.auth.enumeration.ErrorDetails;
import kitkat.auth.exception.error.AuthError;
import kitkat.auth.exception.AuthorizationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ AuthorizationException.class })
    public ResponseEntity<Object> handleAuthorizationException(AuthorizationException authorizationException) {
        LOGGER.error(authorizationException.getDescription(), authorizationException);
        return new ResponseEntity<>(authorizationException.getErrorDetails(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { BadCredentialsException.class })
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException badCredentialsException) {
        AuthError authError = AuthError.BAD_CREDENTIALS;
        LOGGER.error(authError.getDescription(), badCredentialsException);
        return new ResponseEntity<>(new ErrorDetails(authError.getErrorCode(), authError.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
