package kitkat.auth.service

import kitkat.auth.dao.AuthRoleToPermissionsDao
import kitkat.auth.dao.AuthRoleToUsernameDao
import kitkat.auth.model.dto.AuthRoleToAuthoritiesDto
import kitkat.auth.model.dto.AuthRoleToUsernameDto
import spock.lang.Specification

class AuthRoleServiceSpec extends Specification {

    AuthRoleToUsernameDao authRoleToUsernameDao
    AuthRoleToPermissionsDao authRoleToPermissionsDao
    AuthRoleService authRoleService

    def setup() {
        authRoleToUsernameDao = Mock()
        authRoleToPermissionsDao = Mock()
        authRoleService = new AuthRoleServiceImpl(authRoleToUsernameDao, authRoleToPermissionsDao)
    }

    def "Should delegate call to get auth role by username to dao"() {
        given:
            def username = "username"
            AuthRoleToUsernameDto dto = Mock()

        when:
            def response = authRoleService.getAuthRoleByUsername(username)

        then:
            1 * authRoleToUsernameDao.getAuthRoleByUsername(username) >> dto
            0 * _

        and:
            response == dto
    }

    def "Should delegate call to get permissions by auth role to dao"() {
        given:
            def role = "ROLE"
            AuthRoleToAuthoritiesDto dto = Mock()

        when:
            def response = authRoleService.getPermissionsByAuthRole(role)

        then:
            1 * authRoleToPermissionsDao.getPermissionsByAuthRole(role) >> dto
            0 * _

        and:
            response == dto
    }
}
