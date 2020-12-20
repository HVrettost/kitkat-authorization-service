package kitkat.auth.gateway;

import java.util.Map;

import kitkat.auth.config.properties.UserServiceConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kitkat.auth.model.dto.UserDto;

@Component
public class UserServiceGatewayImpl implements UserServiceGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceGatewayImpl.class);

    private static final String USER_URI = "/api/user";

    private final UserServiceConfigProperties userServiceConfigProperties;
    private final RestTemplate restTemplate;

    public UserServiceGatewayImpl(UserServiceConfigProperties userServiceConfigProperties,
                                  RestTemplate restTemplate) {
        this.userServiceConfigProperties = userServiceConfigProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        try {
            return restTemplate.getForEntity(constructUri(USER_URI, Map.of("username", username)), UserDto.class).getBody();
        } catch (Exception ex) {
            LOGGER.error("Error when calling user service to fetch user information for username: " + username, ex);
            return new UserDto();
        }
    }

    private String constructUri(String resourcePath, Map<String, String> queryParameters) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(userServiceConfigProperties.getHost() + resourcePath);
        queryParameters.forEach(builder::queryParam);

        return builder.build().toString();
    }
}
