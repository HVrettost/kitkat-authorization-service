package kitkat.auth.service;

import kitkat.auth.model.dto.AuthTokenDto;

public interface AuthTokenService {

    void invalidateToken(AuthTokenDto authTokenDto);

    AuthTokenDto invalidateTokenIfExistsAndSaveNewToken(AuthTokenDto authTokenDto);

    AuthTokenDto updateAccessToken(AuthTokenDto authTokenDto);

    AuthTokenDto getTokenByUsername(String username);
}
