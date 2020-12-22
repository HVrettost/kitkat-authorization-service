package kitkat.auth.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kitkat.auth.config.properties.UserServiceConfigProperties;
import kitkat.auth.model.dto.UserCredentialsDto;

@Component
public class UserServiceGatewayImpl implements UserServiceGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceGatewayImpl.class);

    private static final String USERS_BASE_URI = "/api/users";
    private static final String USER_CREDENTIALS_URI = "/%s/credentials";

    private final UserServiceConfigProperties userServiceConfigProperties;
    private final RestTemplate restTemplate;

    public UserServiceGatewayImpl(UserServiceConfigProperties userServiceConfigProperties,
                                  RestTemplate restTemplate) {
        this.userServiceConfigProperties = userServiceConfigProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public UserCredentialsDto getUserCredentialsByUsername(String username) {
        try {
            return restTemplate.getForEntity(constructUri(USERS_BASE_URI + String.format(USER_CREDENTIALS_URI, username)),
                    UserCredentialsDto.class).getBody();
        } catch (Exception ex) {
            LOGGER.error("Error when calling user service to fetch user information for username: " + username, ex);
            return new UserCredentialsDto();
        }
    }

    private String constructUri(String resourcePath) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(userServiceConfigProperties.getHost() + resourcePath);
        return builder.build().toString();
    }
}
