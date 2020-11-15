package kitkat.auth.service;

import kitkat.auth.model.dto.AuthRoleToAuthoritiesDto;
import kitkat.auth.model.dto.AuthRoleToUsernameDto;

public interface AuthRoleService {

    AuthRoleToUsernameDto getAuthRoleByUsername(String username);

    AuthRoleToAuthoritiesDto getPermissionsByAuthRole(String authRole);
}
