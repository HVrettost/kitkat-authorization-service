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
    INVALID_TOKEN(5000, "Invalid Token", "Token could not be verified by JWT verifier");

    private final int errorCode;
    private final String message;
    private final String description;
}
