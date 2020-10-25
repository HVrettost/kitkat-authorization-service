package kitkat.auth.repository.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "auth_token")
public class AuthToken {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "client_id", unique = true, nullable = false)
    private UUID clientId;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "access_token_expiration_time")
    private Instant accessTokenExpirationTime;

    @Column(name = "refresh_token_expiration_time")
    private Instant refreshTokenExpirationTime;


    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
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

    public Instant getAccessTokenExpirationTime() {
        return accessTokenExpirationTime;
    }

    public void setAccessTokenExpirationTime(Instant accessTokenExpirationTime) {
        this.accessTokenExpirationTime = accessTokenExpirationTime;
    }

    public Instant getRefreshTokenExpirationTime() {
        return refreshTokenExpirationTime;
    }

    public void setRefreshTokenExpirationTime(Instant refreshTokenExpirationTime) {
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }
}
