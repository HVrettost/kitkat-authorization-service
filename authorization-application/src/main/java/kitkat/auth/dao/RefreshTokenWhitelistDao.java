package kitkat.auth.dao;

public interface RefreshTokenWhitelistDao {

    void saveRefreshToken(String refreshToken, String username, String userAgent);

    void invalidateRefreshToken(String username, String userAgent);

    int countTokensByUsernameAndUserAgent(String username, String userAgent);

    void invalidateRefreshTokensByUsername(String username);

    boolean isRefreshTokenExistsByUsername(String username);

    boolean isRefreshTokenExistsByUsernameAndUserAgent(String username, String userAgent);
}
