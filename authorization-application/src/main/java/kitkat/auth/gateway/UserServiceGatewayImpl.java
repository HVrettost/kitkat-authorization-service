package kitkat.auth.gateway;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kitkat.auth.model.dto.UserDto;

@Component
public class UserServiceGatewayImpl implements UserServiceGateway {

    private static final String USER_SERVICE_BASE_URL = "http://localhost:8905";
    private static final String USER_URI = "/api/user";

    private final RestTemplate restTemplate;

    public UserServiceGatewayImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<UserDto> getUserByUsername(String username) {
        return  restTemplate.getForEntity(constructUri(USER_URI, Map.of("username", username)), UserDto.class);
    }

    private String constructUri(String resourcePath, Map<String, String> queryParameters) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(USER_SERVICE_BASE_URL + resourcePath);
        queryParameters.forEach(builder::queryParam);

        return builder.build().toString();
    }
}
