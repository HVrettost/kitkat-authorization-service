package kitkat.auth.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "kitkat.auth.security.cors")
@PropertySource("classpath:${spring.profiles.active}/security-config-${spring.profiles.active}.properties")
@Getter
@Setter
public class SecurityConfigProperties {

    private List<String> allowedOrigins;
    private List<String> exposedHeaders;
    private String mapping;
    private List<String> allowedMethods;
}
