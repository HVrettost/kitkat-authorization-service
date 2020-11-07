package kitkat.auth.model.dto;

public class AuthenticationResponseDto {

    private final String username;
    private final String accessToken;
    private final String refreshToken;

    public AuthenticationResponseDto(String username, String accessToken, String refreshToken) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}