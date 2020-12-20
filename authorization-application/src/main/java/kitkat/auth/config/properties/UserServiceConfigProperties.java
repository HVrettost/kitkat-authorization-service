package kitkat.auth.config.properties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "kitkat.auth.user-service")
@PropertySource("classpath:${spring.profiles.active}/user-service-${spring.profiles.active}.properties")
@Getter
@Setter
public class UserServiceConfigProperties {

    private String host;
}
