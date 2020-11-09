package kitkat.auth.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(schema = "AUTH", name = "AUTH_TOKEN")
public class AuthToken {

    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "TOKEN_TYPE")
    private String tokenType;


    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

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
