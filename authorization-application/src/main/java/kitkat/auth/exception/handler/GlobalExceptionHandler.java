package kitkat.auth.exception.handler;

import kitkat.auth.exception.AuthenticationException;
import kitkat.auth.exception.error.AuthenticationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import kitkat.auth.enumeration.ErrorDetails;
import kitkat.auth.exception.error.AuthorizationError;
import kitkat.auth.exception.AuthorizationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ AuthorizationException.class })
    public ResponseEntity<ErrorDetails> handleAuthorizationException(AuthorizationException authorizationException) {
        LOGGER.error(authorizationException.getDescription(), authorizationException);
        return new ResponseEntity<>(authorizationException.getErrorDetails(), authorizationException.getHttpStatus());
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<ErrorDetails> handleAuthenticationException(AuthenticationException authenticationException) {
        LOGGER.error(authenticationException.getDescription(), authenticationException);
        return new ResponseEntity<>(authenticationException.getErrorDetails(), authenticationException.getHttpStatus());
    }

    @ExceptionHandler({ InternalAuthenticationServiceException.class })
    public ResponseEntity<ErrorDetails> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException exception) {
        LOGGER.error(exception.getCause().getMessage(), exception);
        AuthenticationError error = AuthenticationError.BAD_CREDENTIALS;
        return new ResponseEntity<>(new ErrorDetails(error.getErrorCode(), error.getMessage()), error.getHttpStatus());
    }


    @ExceptionHandler(value = { BadCredentialsException.class })
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(BadCredentialsException badCredentialsException) {
        AuthenticationError error = AuthenticationError.BAD_CREDENTIALS;
        LOGGER.error(error.getDescription(), badCredentialsException);
        return new ResponseEntity<>(new ErrorDetails(error.getErrorCode(), error.getMessage()), error.getHttpStatus());
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ErrorDetails> handleGenericException(Exception exception) {
        AuthorizationError error = AuthorizationError.GENERIC_ERROR;
        LOGGER.error(error.getDescription(), exception);
        return new ResponseEntity<>(new ErrorDetails(error.getErrorCode(), error.getMessage()), error.getHttpStatus());
    }
}
