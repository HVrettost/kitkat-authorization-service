package kitkat.auth.repository;

import kitkat.auth.model.entity.AuthRoleToUsername;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRoleToUsernameRepository extends CrudRepository<AuthRoleToUsername, String> {

    Optional<AuthRoleToUsername> findByUsername(@Param("username") String username);
}
