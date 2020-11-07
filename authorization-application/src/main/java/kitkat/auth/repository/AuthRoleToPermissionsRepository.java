package kitkat.auth.repository;

import kitkat.auth.model.entity.AuthRoleToPermissions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthRoleToPermissionsRepository extends CrudRepository<AuthRoleToPermissions, UUID> {

    Optional<AuthRoleToPermissions> findByRole(@Param("role") String authRole);
}
