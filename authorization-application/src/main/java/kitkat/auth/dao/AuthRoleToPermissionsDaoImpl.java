package kitkat.auth.dao;

import kitkat.auth.mapper.AuthRoleToAuthoritiesMapper;
import kitkat.auth.model.dto.AuthRoleToAuthoritiesDto;
import kitkat.auth.model.entity.AuthRoleToAuthorities;
import kitkat.auth.repository.AuthRoleToAuthoritiesRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
        Optional<AuthRoleToAuthorities> authRoleToPermissions = authRoleToAuthoritiesRepository.findByRole(authRole);
        return authRoleToAuthoritiesMapper.toDto(authRoleToPermissions.orElse(new AuthRoleToAuthorities()));
    }
}
