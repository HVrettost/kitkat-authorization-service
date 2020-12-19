package kitkat.auth.gateway;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kitkat.auth.model.dto.UserDto;

@Component
public class UserServiceGatewayImpl implements UserServiceGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceGatewayImpl.class);

    private static final String USER_SERVICE_BASE_URL = "http://localhost:8905";
    private static final String USER_URI = "/api/user";

    private final RestTemplate restTemplate;

    public UserServiceGatewayImpl(RestTemplate restTemplate) {
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
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(USER_SERVICE_BASE_URL + resourcePath);
        queryParameters.forEach(builder::queryParam);

        return builder.build().toString();
    }
}
