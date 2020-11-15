package kitkat.auth.mapper;

import org.springframework.stereotype.Component;

import kitkat.auth.model.entity.RefreshTokenWhitelist;

@Component
public class RefreshTokenWhitelistMapper {

    public RefreshTokenWhitelist toEntity(String refreshToken, String username, String userAgent) {
        RefreshTokenWhitelist refreshTokenWhitelist = new RefreshTokenWhitelist();
        refreshTokenWhitelist.setUsername(username);
        refreshTokenWhitelist.setRefreshToken(refreshToken);
        refreshTokenWhitelist.setUserAgent(userAgent);

        return refreshTokenWhitelist;
    }
}
