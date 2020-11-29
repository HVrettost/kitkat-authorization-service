package kitkat.auth.dao;

import org.springframework.stereotype.Component;

import kitkat.auth.mapper.RefreshTokenWhitelistMapper;
import kitkat.auth.model.entity.RefreshTokenWhitelist;
import kitkat.auth.repository.AuthTokenRepository;

@Component
public class RefreshTokenWhitelistDaoImpl implements RefreshTokenWhitelistDao {

    private final AuthTokenRepository authTokenRepository;
    private final RefreshTokenWhitelistMapper refreshTokenWhitelistMapper;

    public RefreshTokenWhitelistDaoImpl(AuthTokenRepository authTokenRepository,
                                        RefreshTokenWhitelistMapper refreshTokenWhitelistMapper) {
        this.authTokenRepository = authTokenRepository;
        this.refreshTokenWhitelistMapper = refreshTokenWhitelistMapper;
    }

    @Override
    public void saveRefreshToken(String refreshToken, String username, String userAgent) {
        RefreshTokenWhitelist refreshTokenWhitelist = refreshTokenWhitelistMapper.toEntity(refreshToken, username, userAgent);
        authTokenRepository.save(refreshTokenWhitelist);
    }

    @Override
    public void invalidateRefreshToken(String username, String userAgent) {
        authTokenRepository.deleteByUsernameAndUserAgent(username, userAgent);
    }

    @Override
    public int countTokensByUsernameAndUserAgent(String username, String userAgent) {
        return authTokenRepository.countByUsernameAndUserAgent(username, userAgent);
    }

    @Override
    public void invalidateRefreshTokensByUsername(String username) {
        authTokenRepository.deleteAllByUsername(username);
    }

    @Override
    public boolean isRefreshTokenExistsByUsername(String username) {
        return authTokenRepository.existsByUsername(username);
    }

    @Override
    public boolean isRefreshTokenExistsByUsernameAndUserAgent(String username, String userAgent) {
        return authTokenRepository.existsByUsernameAndUserAgent(username, userAgent);
    }
}
