package kitkat.auth.dao;

import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.exception.error.AuthError;
import kitkat.auth.mapper.AuthRoleToUsernameMapper;
import kitkat.auth.model.dto.AuthRoleToUsernameDto;
import kitkat.auth.model.entity.AuthRoleToUsername;
import kitkat.auth.repository.AuthRoleToUsernameRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthRoleToUsernameDaoImpl implements AuthRoleToUsernameDao {

    private final AuthRoleToUsernameRepository authRoleToUsernameRepository;
    private final AuthRoleToUsernameMapper authRoleToUsernameMapper;

    public AuthRoleToUsernameDaoImpl(AuthRoleToUsernameRepository authRoleToUsernameRepository,
                                     AuthRoleToUsernameMapper authRoleToUsernameMapper) {
        this.authRoleToUsernameRepository = authRoleToUsernameRepository;
        this.authRoleToUsernameMapper = authRoleToUsernameMapper;
    }

    @Override
    public AuthRoleToUsernameDto getAuthRoleByUsername(String username) {
        AuthRoleToUsername authRoleToUsername = authRoleToUsernameRepository.findByUsername(username)
                .orElseThrow(() -> new AuthorizationException(AuthError.AUTH_ROLE_FOR_GIVEN_USERNAME_NOT_FOUND));
        return authRoleToUsernameMapper.toDto(authRoleToUsername);
    }
}
