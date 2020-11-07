package kitkat.auth.service;

import kitkat.auth.model.dto.AuthTokenDto;

public interface AuthTokenService {

    void invalidateToken(String username);

    AuthTokenDto invalidateTokenIfExistsAndSaveNewToken(AuthTokenDto authTokenDto);

    AuthTokenDto updateAccessToken(AuthTokenDto authTokenDto);

    AuthTokenDto getTokenByUsername(String username);
}
