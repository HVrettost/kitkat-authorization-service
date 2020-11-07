package kitkat.auth.gateway;

import kitkat.auth.model.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class UserServiceGatewayImpl implements UserServiceGateway {

    private static final String USER_URI = "/api/user";

    private String userServiceBaseUrl = "http://localhost:8905";

    private final RestTemplate restTemplate;

    public UserServiceGatewayImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<UserDto> getUserByUsername(String username) {
        return  restTemplate.getForEntity(constructUri(USER_URI, Map.of("username", username)), UserDto.class);
    }

    private String constructUri(String resourcePath, Map<String, String> queryParameters) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(userServiceBaseUrl+resourcePath);
        queryParameters.entrySet()
                .stream()
                .forEach(entry -> builder.queryParam(entry.getKey(), entry.getValue()));

        return builder.build().toString();
    }
}
