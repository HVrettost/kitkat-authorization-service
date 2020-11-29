package kitkat.auth.dao

import kitkat.auth.exception.error.AuthError
import spock.lang.Specification

import kitkat.auth.exception.AuthorizationException
import kitkat.auth.mapper.AuthRoleToAuthoritiesMapper
import kitkat.auth.model.dto.AuthRoleToAuthoritiesDto
import kitkat.auth.model.entity.AuthRoleToAuthorities
import kitkat.auth.repository.AuthRoleToAuthoritiesRepository

class AuthRoleToPermissionsDaoSpec extends Specification {

    AuthRoleToAuthoritiesRepository authRoleToAuthoritiesRepository
    AuthRoleToAuthoritiesMapper authRoleToAuthoritiesMapper
    AuthRoleToPermissionsDao authRoleToPermissionsDao

    def setup() {
        authRoleToAuthoritiesRepository = Mock()
        authRoleToAuthoritiesMapper = Mock()
        authRoleToPermissionsDao = new AuthRoleToPermissionsDaoImpl(authRoleToAuthoritiesRepository, authRoleToAuthoritiesMapper)
    }

    def "Should retrieve permissions of certain role and convert it to DTO object successful"() {
        given:
            def role = "ROLE"
            Optional<AuthRoleToAuthorities> authRolePermissions = Optional.ofNullable(new AuthRoleToAuthorities())
            AuthRoleToAuthoritiesDto authRoleToAuthoritiesDto = Mock()

        when:
            def response = authRoleToPermissionsDao.getPermissionsByAuthRole(role)

        then:
            1 * authRoleToAuthoritiesRepository.findByRole(role) >> authRolePermissions
            1 * authRoleToAuthoritiesMapper.toDto(authRolePermissions.get()) >> authRoleToAuthoritiesDto
            0 * _

        and:
            response == authRoleToAuthoritiesDto
    }

    def "Should throw AuthorizationException if retrieving permissions from database for a role is not successful"() {
        given:
            def role = "ROLE"

        when:
            authRoleToPermissionsDao.getPermissionsByAuthRole(role)

        then:
            1 * authRoleToAuthoritiesRepository.findByRole(role) >> Optional.empty()
            0 * _

        and:
            AuthorizationException authEx = thrown(AuthorizationException)
            with(authEx) {
                errorDetails.message == AuthError.PERMISSIONS_FOR_GIVEN_AUTH_ROLE_NOT_FOUND.message
                errorDetails.errorCode == AuthError.PERMISSIONS_FOR_GIVEN_AUTH_ROLE_NOT_FOUND.errorCode
                description == AuthError.PERMISSIONS_FOR_GIVEN_AUTH_ROLE_NOT_FOUND.description
            }
    }
}
