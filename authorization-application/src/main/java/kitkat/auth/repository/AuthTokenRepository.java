package kitkat.auth.repository;

import kitkat.auth.repository.model.AuthToken;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthTokenRepository extends CrudRepository<AuthToken, UUID> {

}
