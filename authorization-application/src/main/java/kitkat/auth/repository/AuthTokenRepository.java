package kitkat.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kitkat.auth.model.entity.RefreshTokenWhitelist;

@Repository
public interface AuthTokenRepository extends JpaRepository<RefreshTokenWhitelist, String> {

    @Modifying
    void deleteByUsernameAndUserAgent(@Param("username") String username,
                                      @Param("userAgent") String userAgent);

    @Modifying
    void deleteAllByUsername(@Param("username") String username);

    int countByUsernameAndUserAgent(@Param("username") String username,
                                    @Param("userAgent") String userAgent);

    boolean existsByUsernameAndUserAgent(@Param("username") String username,
                                                                 @Param("userAgent") String userAgent);

    boolean existsByUsername(@Param("username") String username);
}
