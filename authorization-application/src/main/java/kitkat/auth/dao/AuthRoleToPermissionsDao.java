package kitkat.auth.dao;

import kitkat.auth.model.dto.AuthRoleToPermissionsDto;

public interface AuthRoleToPermissionsDao {

    AuthRoleToPermissionsDto getPermissionsByAuthRole(String authRole);
}
