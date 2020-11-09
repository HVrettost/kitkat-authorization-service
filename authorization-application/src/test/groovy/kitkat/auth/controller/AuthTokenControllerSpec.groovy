package kitkat.auth.controller

import kitkat.auth.model.dto.AuthTokenDto
import kitkat.auth.model.dto.AuthenticationRequestDto
import kitkat.auth.service.AuthTokenService
import kitkat.auth.service.AuthenticationService
import spock.lang.Specification

class AuthTokenControllerSpec extends Specification {

    AuthenticationService authenticationService
    AuthTokenService authTokenService
    AuthTokenController authTokenController

    def setup() {
        authenticationService = Mock()
        authTokenService = Mock()
        authTokenController = new AuthTokenController(authenticationService, authTokenService)
    }

    def "Should delegate call to service in order to authenticate"() {
        given:
            AuthenticationRequestDto request = Mock()
            AuthTokenDto token = Mock()

        when:
            def response = authTokenController.authenticate(request)

        then:
            1 * authenticationService.authenticate(request) >> token
            0 * _

        and:
            response == token
    }

    def "Should delegate call to service in order to invalidate token"() {
        given:
            AuthTokenDto authTokenDto = Mock()

        when:
            authTokenController.invalidateToken(authTokenDto)

        then:
            1 * authTokenService.invalidateToken(authTokenDto)
            0 * _
    }

    def "Should delegate call to service in order to update access token"() {
        given:
            AuthTokenDto authTokenRequestDto = Mock()
            AuthTokenDto authTokenResponseDto = Mock()

        when:
            def response = authTokenController.updateAccessToken(authTokenRequestDto)

        then:
            1 * authTokenService.updateAccessToken(authTokenRequestDto) >> authTokenResponseDto
            0 * _

        and:
            response == authTokenResponseDto
    }
}
