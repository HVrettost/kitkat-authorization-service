package kitkat.auth.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kitkat.auth.config.properties.UserServiceConfigProperties;
import kitkat.auth.model.dto.UserCredentialsDto;

@Component
public class UserServiceGatewayImpl implements UserServiceGateway {

    private static final String USERS_BASE_URI = "/api/users";
    private static final String USER_CREDENTIALS_URI = "/%s/credentials";

    private final UserServiceConfigProperties userServiceConfigProperties;
    private final RestTemplate restTemplate;

    public UserServiceGatewayImpl(UserServiceConfigProperties userServiceConfigProperties,
                                  RestTemplate restTemplate) {
        this.userServiceConfigProperties = userServiceConfigProperties;
        this.restTemplate = restTemplate;
    }

    //No need to handle exception here as it will be caught by spring security and a InternalAuthenticationServiceException will be thrown
    //The InternalAuthenticationServiceException will be handled in GlobalExceptionHandler.java
    //Logging of exception will also happen in GlobalExceptionHandler.java
    @Override
    public UserCredentialsDto getUserCredentialsByUsername(String username) {
        return restTemplate.getForEntity(constructUri(USERS_BASE_URI + String.format(USER_CREDENTIALS_URI, username)),
                    UserCredentialsDto.class).getBody();
    }

    private String constructUri(String resourcePath) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(userServiceConfigProperties.getHost() + resourcePath);
        return builder.build().toString();
    }
}
