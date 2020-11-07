package kitkat.auth.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(schema = "AUTH", name = "AUTH_TOKEN")
public class AuthToken {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "AUTH_TOKEN_ID")
    private UUID authTokenId;

    @Column(name = "TOKEN_TYPE")
    private String tokenType;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

    @Column(name = "REFRESH_TOKEN")
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