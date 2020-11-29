package kitkat.auth.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthError {

    REFRESH_TOKEN_NOT_FOUND(1000, "Refresh Token Not Found", "Could not find refresh token in database given the username and userAgent"),
    REFRESH_TOKENS_NOT_FOUND(2000, "One Or More Refresh Tokens Not Found", "Could not find one or more refresh tokens in database given the username"),
    BAD_CREDENTIALS(3000, "Username and password could not be verified", ""),
    TOKEN_EXPIRED(4000, "Token has expired", ""),
    INVALID_TOKEN(5000, "Invalid Token", "Token could not be verified by JWT verifier"),
    PERMISSIONS_FOR_GIVEN_AUTH_ROLE_NOT_FOUND(6000, "Permissions not found for given role", "Permissions for given role could not be retrieved from database"),
    AUTH_ROLE_FOR_GIVEN_USERNAME_NOT_FOUND(7000, "Role not found for given username", "Role for given username could not be retrieved from database"),
    USER_AGENT_NOT_FOUND(8000, "Could not find user agent", "Could not extract user agent from User-Agent header"),
    TOKEN_COULD_NOT_BE_EXTRACTED(9000, "Token could not be extracted from Authorization header", "Access Token or Refresh Token could not be extracted from Authorization header");


    private final int errorCode;
    private final String message;
    private final String description;
}
