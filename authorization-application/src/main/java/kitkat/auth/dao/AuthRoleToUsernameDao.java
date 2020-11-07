package kitkat.auth.dao;

import kitkat.auth.model.dto.AuthRoleToUsernameDto;

public interface AuthRoleToUsernameDao {

    AuthRoleToUsernameDto getAuthRoleByUsername(String username);
}
