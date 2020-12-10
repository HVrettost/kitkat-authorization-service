package kitkat.auth.controller

import kitkat.auth.model.dto.AuthenticationRequestDto
import kitkat.auth.service.AuthTokenService
import kitkat.auth.service.AuthenticationService
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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
            HttpServletRequest request = Mock()
            HttpServletResponse response = Mock()
            AuthenticationRequestDto authRequestDto = Mock()

        when:
            authTokenController.authenticate(request, authRequestDto)

        then:
            1 * authenticationService.authenticate(authRequestDto)
            1 * response.setStatus(HttpServletResponse.SC_NO_CONTENT)
            0 * _
    }

    def "Should delegate call to service in order to invalidate token"() {
        given:
            HttpServletRequest request = Mock()
            HttpServletResponse response = Mock()

        when:
            authTokenController.invalidateRefreshToken(request)

        then:
            1 * authTokenService.invalidateRefreshToken(request)
            1 * response.setStatus(HttpServletResponse.SC_NO_CONTENT)
            0 * _
    }

    def "Should delegate call to service in order to update access token"() {
        given:
            HttpServletRequest request = Mock()
            HttpServletResponse response = Mock()

        when:
            authTokenController.updateAccessToken(request)

        then:
            1 * authTokenService.updateCookieHeaderForAccessToken(request)
            1 * response.setStatus(HttpServletResponse.SC_NO_CONTENT)
            0 * _
    }

    def "Should delegate call to service in order to invalidate all refresh tokens of the user"() {
        given:
            HttpServletRequest request = Mock()
            HttpServletResponse response = Mock()

        when:
            authTokenController.invalidateRefreshTokensByUsername(request)

        then:
            1 * authTokenService.invalidateRefreshTokensByUsername(request)
            1 * response.setStatus(HttpServletResponse.SC_NO_CONTENT)
            0 * _
    }
}
