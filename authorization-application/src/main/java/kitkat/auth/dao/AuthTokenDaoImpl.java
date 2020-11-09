package kitkat.auth.dao;

import kitkat.auth.mapper.AuthTokenMapper;
import kitkat.auth.model.dto.AuthTokenDto;
import kitkat.auth.model.entity.AuthToken;
import kitkat.auth.repository.AuthTokenRepository;

import org.springframework.stereotype.Component;

@Component
public class AuthTokenDaoImpl implements AuthTokenDao {

    private final AuthTokenRepository authTokenRepository;
    private final AuthTokenMapper authTokenMapper;

    public AuthTokenDaoImpl(AuthTokenRepository authTokenRepository,
                            AuthTokenMapper authTokenMapper) {
        this.authTokenRepository = authTokenRepository;
        this.authTokenMapper = authTokenMapper;
    }

    @Override
    public AuthTokenDto saveAuthToken(AuthTokenDto authTokenDto) {
        AuthToken authToken = authTokenMapper.toEntity(authTokenDto);
        authTokenDto = authTokenMapper.toDto(authTokenRepository.save(authToken));

        return authTokenDto;
    }

    @Override
    public void updateAccessTokenByUsername(AuthTokenDto authTokenDto) {
        authTokenRepository.updateAccessTokenByUsername(authTokenDto.getUsername(), authTokenDto.getAccessToken());
    }

    @Override
    public void invalidateToken(String username) {
        authTokenRepository.deleteByUsername(username);
    }
}
