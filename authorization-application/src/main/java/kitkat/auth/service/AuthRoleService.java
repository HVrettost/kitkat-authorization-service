package kitkat.auth.service;

import kitkat.auth.model.dto.AuthRoleToPermissionsDto;
import kitkat.auth.model.dto.AuthRoleToUsernameDto;

public interface AuthRoleService {

    AuthRoleToUsernameDto getAuthRoleByUsername(String username);

    AuthRoleToPermissionsDto getPermissionsByAuthRole(String authRole);
}
