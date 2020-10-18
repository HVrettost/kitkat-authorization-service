package kitkat.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ApplicationBeans {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
