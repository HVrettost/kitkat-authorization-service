package kitkat.auth.service;

import kitkat.auth.dao.AuthTokenDao;
import kitkat.auth.model.dto.AuthTokenDto;
import kitkat.auth.util.JwtUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenDao authTokenDao;
    private final JwtUtils jwtUtils;

    public AuthTokenServiceImpl(AuthTokenDao authTokenDao,
                                JwtUtils jwtUtils) {
        this.authTokenDao = authTokenDao;
        this.jwtUtils = jwtUtils;
    }

    @Override
    @Transactional
    public void invalidateToken(String username) {
        authTokenDao.invalidateToken(username);
    }

    public AuthTokenDto invalidateTokenIfExistsAndSaveNewToken(AuthTokenDto authTokenDto) {
        authTokenDao.invalidateToken(authTokenDto.getUsername());
        return authTokenDao.saveAuthToken(authTokenDto);
    }

    @Override
    @Transactional
    public AuthTokenDto updateAccessToken(AuthTokenDto authTokenDto) {
        String accessToken = jwtUtils.generateAccessToken(authTokenDto.getUsername());
        authTokenDto.setAccessToken(accessToken);
        authTokenDao.updateAccessTokenByUsername(authTokenDto);

        return authTokenDto;
    }

    @Override
    public AuthTokenDto getTokenByUsername(String username) {
        return authTokenDao.getTokenByUsername(username);
    }
}
