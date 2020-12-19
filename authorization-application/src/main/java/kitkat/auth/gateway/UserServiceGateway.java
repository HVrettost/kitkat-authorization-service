package kitkat.auth.gateway;

import kitkat.auth.model.dto.UserDto;

public interface UserServiceGateway {

    UserDto getUserByUsername(String username);
}
