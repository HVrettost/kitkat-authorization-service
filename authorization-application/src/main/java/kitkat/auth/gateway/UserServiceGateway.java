package kitkat.auth.gateway;

import kitkat.auth.model.dto.UserCredentialsDto;

public interface UserServiceGateway {

    UserCredentialsDto getUserCredentialsByUsername(String username);
}
