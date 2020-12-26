package kitkat.auth.service

import kitkat.auth.validator.UsernameValidator
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

import spock.lang.Specification

import kitkat.auth.model.dto.AuthenticationRequestDto

class AuthenticationServiceImplSpec extends Specification {

    AuthenticationManager authenticationManager
    UsernameValidator usernameValidator
    AuthenticationService authenticationService

    def setup() {
        authenticationManager = Mock()
        usernameValidator = Mock()
        authenticationService = new AuthenticationServiceImpl(authenticationManager, usernameValidator)
    }

    def "Should authenticate user"() {
        given:
            AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto("username", "password")

        when:
            authenticationService.authenticate(authenticationRequestDto)

        then:
            1 * usernameValidator.validate(authenticationRequestDto.username)
            1 * authenticationManager.authenticate(_ as UsernamePasswordAuthenticationToken)
            0 * _
    }
}
