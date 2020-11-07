package kitkat.auth.dao;

import kitkat.auth.mapper.AuthRoleToPermissionsMapper;
import kitkat.auth.model.dto.AuthRoleToPermissionsDto;
import kitkat.auth.model.entity.AuthRoleToPermissions;
import kitkat.auth.repository.AuthRoleToPermissionsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthRoleToPermissionsDaoImpl implements AuthRoleToPermissionsDao {

    private final AuthRoleToPermissionsRepository authRoleToPermissionsRepository;
    private final AuthRoleToPermissionsMapper authRoleToPermissionsMapper;

    public AuthRoleToPermissionsDaoImpl(AuthRoleToPermissionsRepository authRoleToPermissionsRepository,
                                        AuthRoleToPermissionsMapper authRoleToPermissionsMapper) {
        this.authRoleToPermissionsRepository = authRoleToPermissionsRepository;
        this.authRoleToPermissionsMapper = authRoleToPermissionsMapper;
    }

    @Override
    public AuthRoleToPermissionsDto getPermissionsByAuthRole(String authRole) {
        Optional<AuthRoleToPermissions> authRoleToPermissions = authRoleToPermissionsRepository.findByRole(authRole);
        return authRoleToPermissionsMapper.toDto(authRoleToPermissions.orElse(new AuthRoleToPermissions()));
    }
}
