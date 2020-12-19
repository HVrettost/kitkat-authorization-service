package kitkat.auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "kitkat.auth.jwt")
@PropertySource("classpath:${spring.profiles.active}/jwt-${spring.profiles.active}.properties")
@Getter
@Setter
public class JWTConfigProperties {

    private String issuer;
    private String secret;
    private long accessTokenExpirationIntervalInMillis;
    private long refreshTokenExpirationIntervalInMillis;
    private long leeway;
}
