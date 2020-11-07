package kitkat.auth.gateway;

import kitkat.auth.model.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserServiceGateway {

    ResponseEntity<UserDto> getUserByUsername(String username);
}
