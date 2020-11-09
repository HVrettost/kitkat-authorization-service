package kitkat.auth.service;

import kitkat.auth.dao.AuthTokenDao;
import kitkat.auth.enumeration.TokenType;
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
    public void invalidateToken(AuthTokenDto authTokenDto) {
        authTokenDao.invalidateToken(authTokenDto.getUsername());
    }

    public AuthTokenDto invalidateTokenIfExistsAndSaveNewToken(AuthTokenDto authTokenDto) {
        authTokenDao.invalidateToken(authTokenDto.getUsername());
        return authTokenDao.saveAuthToken(authTokenDto);
    }

    @Override
    @Transactional
    public AuthTokenDto updateAccessToken(AuthTokenDto authTokenDto) {
        String token = jwtUtils.generateAccessToken(authTokenDto.getUsername(), jwtUtils.extractPermissionsClaim(authTokenDto.getAccessToken()));
        authTokenDto.setAccessToken(token);
        authTokenDao.updateAccessTokenByUsername(authTokenDto);

        return authTokenDto;
    }

    @Override
    public AuthTokenDto createAuthTokenDto(String username, String permissions) {
        AuthTokenDto authTokenDto = new AuthTokenDto();
        authTokenDto.setTokenType(TokenType.BEARER.getValue());
        authTokenDto.setUsername(username);
        authTokenDto.setAccessToken(jwtUtils.generateAccessToken(username, permissions));
        authTokenDto.setRefreshToken(jwtUtils.generateRefreshToken(username));

        return authTokenDto;
    }
}
