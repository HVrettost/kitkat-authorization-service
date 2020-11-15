package kitkat.auth.repository;

import kitkat.auth.model.entity.AuthRoleToAuthorities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthRoleToAuthoritiesRepository extends CrudRepository<AuthRoleToAuthorities, UUID> {

    Optional<AuthRoleToAuthorities> findByRole(@Param("role") String authRole);
}
