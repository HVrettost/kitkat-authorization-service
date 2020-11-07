package kitkat.auth.dao;

import kitkat.auth.model.dto.AuthTokenDto;

public interface AuthTokenDao {

    AuthTokenDto saveAuthToken(AuthTokenDto authTokenDto);

    void invalidateToken(String username);

    AuthTokenDto getTokenByUsername(String username);

    void updateAccessTokenByUsername(AuthTokenDto authTokenDto);
}
