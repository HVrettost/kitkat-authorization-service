package kitkat.auth.mapper

import kitkat.auth.model.dto.AuthRoleToAuthoritiesDto
import kitkat.auth.model.entity.AuthRoleToAuthorities
import org.modelmapper.ModelMapper
import spock.lang.Specification

class AuthRoleToAuthoritiesMapperSpec extends Specification {

    ModelMapper modelMapper
    AuthRoleToAuthoritiesMapper authRoleToAuthoritiesMapper

    def setup() {
        modelMapper = Mock()
        authRoleToAuthoritiesMapper = new AuthRoleToAuthoritiesMapper(modelMapper)
    }

    def "Should map AuthRoleToAuthorities to AuthRoleToAuthoritiesDto"() {
        given:
            AuthRoleToAuthoritiesDto authRoleToAuthoritiesDto = Mock()
            AuthRoleToAuthorities authRoleToAuthorities = Mock()

        when:
            def response = authRoleToAuthoritiesMapper.toDto(authRoleToAuthorities)

        then:
            1 * modelMapper.map(authRoleToAuthorities, AuthRoleToAuthoritiesDto) >> authRoleToAuthoritiesDto
            0 * _

        and:
            response == authRoleToAuthoritiesDto

    }

    def "Should map AuthRoleToAuthoritiesDto to AuthRoleToAuthorities"() {
        given:
            AuthRoleToAuthoritiesDto authRoleToAuthoritiesDto = Mock()
            AuthRoleToAuthorities authRoleToAuthorities = Mock()

        when:
            def response = authRoleToAuthoritiesMapper.toEntity(authRoleToAuthoritiesDto)

        then:
            1 * modelMapper.map(authRoleToAuthoritiesDto, AuthRoleToAuthorities) >> authRoleToAuthorities
            0 * _

        and:
            response == authRoleToAuthorities
    }
}
