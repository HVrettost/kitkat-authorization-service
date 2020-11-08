package kitkat.auth.repository;

import kitkat.auth.model.entity.AuthToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {

    @Modifying
    void deleteByUsername(@Param("username") String username);

    @Modifying
    @Query("UPDATE AuthToken at SET at.accessToken = :accessToken WHERE at.username = :username")
    void updateAccessTokenByUsername(@Param("username") String username,
                                     @Param("accessToken") String accessToken);
}
