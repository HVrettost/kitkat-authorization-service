package kitkat.auth.dao;

import org.springframework.stereotype.Component;

import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.exception.error.AuthorizationError;
import kitkat.auth.mapper.AuthRoleToAuthoritiesMapper;
import kitkat.auth.model.dto.AuthRoleToAuthoritiesDto;
import kitkat.auth.model.entity.AuthRoleToAuthorities;
import kitkat.auth.repository.AuthRoleToAuthoritiesRepository;

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
                .orElseThrow(() -> new AuthorizationException(AuthorizationError.PERMISSIONS_FOR_GIVEN_AUTH_ROLE_NOT_FOUND));
        return authRoleToAuthoritiesMapper.toDto(authRoleToPermissions);
    }
}
