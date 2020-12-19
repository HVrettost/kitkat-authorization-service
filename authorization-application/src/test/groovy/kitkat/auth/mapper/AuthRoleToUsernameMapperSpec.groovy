package kitkat.auth.mapper

import kitkat.auth.model.dto.AuthRoleToUsernameDto
import kitkat.auth.model.entity.AuthRoleToUsername
import org.modelmapper.ModelMapper
import spock.lang.Specification

class AuthRoleToUsernameMapperSpec extends Specification {

    ModelMapper modelMapper
    AuthRoleToUsernameMapper authRoleToUsernameMapper

    def setup() {
        modelMapper = Mock()
        authRoleToUsernameMapper = new AuthRoleToUsernameMapper(modelMapper)
    }

    def "Should map AuthRoleToUsername to AuthRoleToUsernameDto"() {
        given:
            AuthRoleToUsername authRoleToUsername = Mock()
            AuthRoleToUsernameDto authRoleToUsernameDto = Mock()

        when:
            def response = authRoleToUsernameMapper.toDto(authRoleToUsername)

        then:
            1 * modelMapper.map(authRoleToUsername, AuthRoleToUsernameDto) >> authRoleToUsernameDto
            0 * _

        and:
            response == authRoleToUsernameDto
    }

    def "Should map AuthRoleToUsernameDto to AuthRoleToUsername"() {
        given:
            AuthRoleToUsername authRoleToUsername = Mock()
            AuthRoleToUsernameDto authRoleToUsernameDto = Mock()

        when:
            def response = authRoleToUsernameMapper.toEntity(authRoleToUsernameDto)

        then:
            1 * modelMapper.map(authRoleToUsernameDto, AuthRoleToUsername) >> authRoleToUsername
            0 * _

        and:
            response == authRoleToUsername
    }

}
