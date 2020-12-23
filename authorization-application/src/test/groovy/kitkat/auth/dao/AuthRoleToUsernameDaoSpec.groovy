package kitkat.auth.dao

import kitkat.auth.exception.AuthorizationException
import kitkat.auth.exception.error.AuthorizationError
import kitkat.auth.mapper.AuthRoleToUsernameMapper
import kitkat.auth.model.dto.AuthRoleToUsernameDto
import kitkat.auth.model.entity.AuthRoleToUsername
import kitkat.auth.repository.AuthRoleToUsernameRepository
import spock.lang.Specification

class AuthRoleToUsernameDaoSpec extends Specification {

    AuthRoleToUsernameRepository authRoleToUsernameRepository
    AuthRoleToUsernameDao authRoleToUsernameDao
    AuthRoleToUsernameMapper authRoleToUsernameMapper

    def setup() {
        authRoleToUsernameMapper = Mock()
        authRoleToUsernameRepository = Mock()
        authRoleToUsernameDao = new AuthRoleToUsernameDaoImpl(authRoleToUsernameRepository, authRoleToUsernameMapper)
    }

    def "Should retrieve auth role by username from database successfully"() {
        given:
            def username = "username"
            Optional<AuthRoleToUsername> authRoleToUsername = Optional.of(new AuthRoleToUsername())
            AuthRoleToUsernameDto authRoleToUsernameDto = Mock()

        when:
            def response = authRoleToUsernameDao.getAuthRoleByUsername(username)

        then:
            1 * authRoleToUsernameRepository.findByUsername(username) >> authRoleToUsername
            1 * authRoleToUsernameMapper.toDto(authRoleToUsername.get()) >> authRoleToUsernameDto
            0 * _

        and:
            response == authRoleToUsernameDto
    }

    def "Should throw AuthorizationException if not auth role is retrieved from the database"() {
        given:
            def username = "username"
            Optional<AuthRoleToUsername> authRoleToUsername = Optional.empty()

        when:
            authRoleToUsernameDao.getAuthRoleByUsername(username)

        then:
            1 * authRoleToUsernameRepository.findByUsername(username) >> authRoleToUsername
            0 * _

        and:
            AuthorizationException authEx = thrown(AuthorizationException)
            with(authEx) {
                errorDetails.message == AuthorizationError.AUTH_ROLE_FOR_GIVEN_USERNAME_NOT_FOUND.message
                errorDetails.errorCode == AuthorizationError.AUTH_ROLE_FOR_GIVEN_USERNAME_NOT_FOUND.errorCode
                description == AuthorizationError.AUTH_ROLE_FOR_GIVEN_USERNAME_NOT_FOUND.description
            }
    }
}
