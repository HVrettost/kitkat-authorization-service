package kitkat.auth.dao;

import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.exception.error.AuthError;
import kitkat.auth.mapper.AuthRoleToAuthoritiesMapper;
import kitkat.auth.model.dto.AuthRoleToAuthoritiesDto;
import kitkat.auth.model.entity.AuthRoleToAuthorities;
import kitkat.auth.repository.AuthRoleToAuthoritiesRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthRoleToPermissionsDaoImpl implements AuthRoleToPermissionsDao {

    private final AuthRoleToAuthoritiesRepository authRoleToAuthoritiesRepository;
    private final AuthRoleToAuthoritiesMapper authRoleToAuthoritiesMapper;

    public AuthRoleToPermissionsDaoImpl(AuthRoleToAuthoritiesRepository authRoleToAuthoritiesRepository,
                                        AuthRoleToAuthoritiesMapper authRoleToAuthoritiesMapper) {
        this.authRoleToAuthoritiesRepository = authRoleToAuthoritiesRepository;
        this.authRoleToAuthoritiesMapper = authRoleToAuthoritiesMapper;
    }

    @Override
    public AuthRoleToAuthoritiesDto getPermissionsByAuthRole(String authRole) {
        AuthRoleToAuthorities authRoleToPermissions = authRoleToAuthoritiesRepository.findByRole(authRole)
                .orElseThrow(() -> new AuthorizationException(AuthError.PERMISSIONS_FOR_GIVEN_AUTH_ROLE_NOT_FOUND));
        return authRoleToAuthoritiesMapper.toDto(authRoleToPermissions);
    }
}
