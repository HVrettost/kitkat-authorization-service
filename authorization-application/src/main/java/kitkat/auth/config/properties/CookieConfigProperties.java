package kitkat.auth.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "kitkat.auth.cookie")
@PropertySource("classpath:cookie.properties")
@Getter
@Setter
public class CookieConfigProperties {

    private String path;
    private String httpOnly;
    private String domain;
    private String sameSite;
    private String secure;
}
