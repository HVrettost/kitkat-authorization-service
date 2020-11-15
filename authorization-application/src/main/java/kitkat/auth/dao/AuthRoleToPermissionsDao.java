package kitkat.auth.dao;

import kitkat.auth.model.dto.AuthRoleToAuthoritiesDto;

public interface AuthRoleToPermissionsDao {

    AuthRoleToAuthoritiesDto getPermissionsByAuthRole(String authRole);
}
