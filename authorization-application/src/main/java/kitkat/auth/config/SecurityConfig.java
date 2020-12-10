package kitkat.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

import kitkat.auth.filter.AuthorizationFilter;
import kitkat.auth.jwt.util.JwtClaimUtils;
import kitkat.auth.jwt.validator.JwtValidator;
import kitkat.auth.util.HeaderUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private static final String AUTH_ACCESS_TOKEN_URI = "/api/auth/token";
    private static final String AUTH_REFRESH_TOKEN_URI = "/api/auth/token/refresh";
    private static final String AUTH_REFRESH_TOKEN_ALL_URI = "/api/auth/token/all";

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtClaimUtils jwtClaimUtils;
    private final JwtValidator jwtValidator;
    private final ObjectMapper objectMapper;
    private final HeaderUtils headerUtils;

    public SecurityConfig(UserDetailsService userDetailsService,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          JwtClaimUtils jwtClaimUtils,
                          JwtValidator jwtValidator,
                          ObjectMapper objectMapper,
                          HeaderUtils headerUtils) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtClaimUtils = jwtClaimUtils;
        this.jwtValidator = jwtValidator;
        this.objectMapper = objectMapper;
        this.headerUtils = headerUtils;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, AUTH_ACCESS_TOKEN_URI).permitAll()
                .antMatchers(HttpMethod.DELETE, AUTH_ACCESS_TOKEN_URI).permitAll()
                .antMatchers(HttpMethod.PUT, AUTH_REFRESH_TOKEN_URI).permitAll()
                .antMatchers(HttpMethod.DELETE, AUTH_REFRESH_TOKEN_ALL_URI).permitAll()
                .antMatchers(HttpMethod.OPTIONS, AUTH_ACCESS_TOKEN_URI).permitAll()
                .antMatchers(HttpMethod.OPTIONS, AUTH_REFRESH_TOKEN_URI).permitAll()
                .antMatchers(HttpMethod.OPTIONS, AUTH_REFRESH_TOKEN_ALL_URI).permitAll()
                .anyRequest().denyAll()
                .and()
                .addFilterAfter(new AuthorizationFilter(jwtValidator, jwtClaimUtils, objectMapper, headerUtils), FilterSecurityInterceptor.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/auth/token/**")
                .allowedOrigins("https://localhost:8529")
                .allowedMethods(HttpMethod.POST.name(), HttpMethod.DELETE.name(), HttpMethod.PUT.name(), HttpMethod.OPTIONS.name())
                .exposedHeaders(HttpHeaders.SET_COOKIE)
                .allowCredentials(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
