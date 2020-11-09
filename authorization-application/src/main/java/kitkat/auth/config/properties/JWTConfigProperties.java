package kitkat.auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "kitkat.auth.jwt")
@PropertySource("classpath:jwt.properties")
public final class JWTConfigProperties {

    private String issuer;
    private String secret;
    private long accessTokenExpirationIntervalInMillis;
    private long refreshTokenExpirationIntervalInMillis;
    private long leeway;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getAccessTokenExpirationIntervalInMillis() {
        return accessTokenExpirationIntervalInMillis;
    }

    public void setAccessTokenExpirationIntervalInMillis(long accessTokenExpirationIntervalInMillis) {
        this.accessTokenExpirationIntervalInMillis = accessTokenExpirationIntervalInMillis;
    }

    public long getRefreshTokenExpirationIntervalInMillis() {
        return refreshTokenExpirationIntervalInMillis;
    }

    public void setRefreshTokenExpirationIntervalInMillis(long refreshTokenExpirationIntervalInMillis) {
        this.refreshTokenExpirationIntervalInMillis = refreshTokenExpirationIntervalInMillis;
    }

    public long getLeeway() {
        return leeway;
    }

    public void setLeeway(long leeway) {
        this.leeway = leeway;
    }
}
