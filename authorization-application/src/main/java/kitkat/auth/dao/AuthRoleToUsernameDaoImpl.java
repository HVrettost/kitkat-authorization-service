package kitkat.auth.dao;

import kitkat.auth.mapper.AuthRoleToUsernameMapper;
import kitkat.auth.model.dto.AuthRoleToUsernameDto;
import kitkat.auth.model.entity.AuthRoleToUsername;
import kitkat.auth.repository.AuthRoleToUsernameRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
        Optional<AuthRoleToUsername> authRoleToUsername = authRoleToUsernameRepository.findByUsername(username);
        return authRoleToUsernameMapper.toDto(authRoleToUsername.orElse(new AuthRoleToUsername()));
    }
}
