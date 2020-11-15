package kitkat.auth.service;

import org.springframework.stereotype.Service;

import kitkat.auth.dao.AuthRoleToPermissionsDao;
import kitkat.auth.dao.AuthRoleToUsernameDao;
import kitkat.auth.model.dto.AuthRoleToAuthoritiesDto;
import kitkat.auth.model.dto.AuthRoleToUsernameDto;

@Service
public class AuthRoleServiceImpl implements AuthRoleService {

    private final AuthRoleToUsernameDao authRoleToUsernameDao;
    private final AuthRoleToPermissionsDao authRoleToPermissionsDao;

    public AuthRoleServiceImpl(AuthRoleToUsernameDao authRoleToUsernameDao,
                               AuthRoleToPermissionsDao authRoleToPermissionsDao) {
        this.authRoleToUsernameDao = authRoleToUsernameDao;
        this.authRoleToPermissionsDao = authRoleToPermissionsDao;
    }

    @Override
    public AuthRoleToUsernameDto getAuthRoleByUsername(String username) {
        return authRoleToUsernameDao.getAuthRoleByUsername(username);
    }

    @Override
    public AuthRoleToAuthoritiesDto getPermissionsByAuthRole(String authRole) {
        return authRoleToPermissionsDao.getPermissionsByAuthRole(authRole);
    }
}
