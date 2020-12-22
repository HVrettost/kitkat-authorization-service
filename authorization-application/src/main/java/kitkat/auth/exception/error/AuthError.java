package kitkat.auth.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthError {

    REFRESH_TOKEN_NOT_FOUND(1000, "Refresh Token Not Found", "Could not find refresh token in database given the username and userAgent", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKENS_NOT_FOUND(1001, "One Or More Refresh Tokens Not Found", "Could not find one or more refresh tokens in database given the username", HttpStatus.FORBIDDEN),
    BAD_CREDENTIALS(1002, "Username and password could not be verified", "", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(1003, "Token has expired", "", HttpStatus.FORBIDDEN),
    INVALID_TOKEN(1004, "Invalid Token", "Token could not be verified by JWT verifier", HttpStatus.FORBIDDEN),
    PERMISSIONS_FOR_GIVEN_AUTH_ROLE_NOT_FOUND(1005, "Permissions not found for given role", "Permissions for given role could not be retrieved from database", HttpStatus.FORBIDDEN),
    AUTH_ROLE_FOR_GIVEN_USERNAME_NOT_FOUND(1006, "Role not found for given username", "Role for given username could not be retrieved from database", HttpStatus.FORBIDDEN),
    USER_AGENT_NOT_FOUND(1007, "Could not find user agent", "Could not extract user agent from User-Agent header", HttpStatus.FORBIDDEN),
    TOKEN_COULD_NOT_BE_EXTRACTED(1008, "Token could not be extracted from Cookie header", "Access Token or Refresh Token could not be extracted from Cookie header", HttpStatus.FORBIDDEN),
    GRANTED_AUTHORITIES_NOT_FOUND(1009, "Could not find granted authorities for user", "One or more granted authorities were not found for user", HttpStatus.FORBIDDEN);


    private final int errorCode;
    private final String message;
    private final String description;
    private final HttpStatus httpStatus;
}
