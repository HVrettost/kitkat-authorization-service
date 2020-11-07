package kitkat.auth.model.dto;

import java.util.UUID;

public class AuthTokenDto {

    private UUID authTokenId;
    private String tokenType;
    private String username;
    private String accessToken;
    private String refreshToken;

    public UUID getAuthTokenId() {
        return authTokenId;
    }

    public void setAuthTokenId(UUID authTokenId) {
        this.authTokenId = authTokenId;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
