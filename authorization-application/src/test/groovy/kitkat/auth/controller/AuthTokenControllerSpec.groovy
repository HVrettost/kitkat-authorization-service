package kitkat.auth.controller

import javax.servlet.http.HttpServletRequest

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import spock.lang.Specification

import kitkat.auth.model.dto.AuthenticationRequestDto
import kitkat.auth.service.AuthTokenService
import kitkat.auth.service.AuthenticationService

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
            HttpServletRequest servletRequest = Mock()
            AuthenticationRequestDto authRequestDto = new AuthenticationRequestDto("username", "password")
            HttpHeaders httpHeaders = new HttpHeaders()

        when:
            ResponseEntity response = authTokenController.generateAccessToken(servletRequest, authRequestDto)

        then:
            1 * authenticationService.authenticate(authRequestDto)
            1 * authTokenService.createCookieHeadersForAuthorization(servletRequest, authRequestDto.username) >> httpHeaders
            0 * _

        and:
            with(response) {
                it.statusCode == HttpStatus.NO_CONTENT
                it.headers == httpHeaders
            }
    }

    def "Should delegate call to service in order to update access token"() {
        given:
            HttpServletRequest request = Mock()

        when:
            def response = authTokenController.updateAccessToken(request)

        then:
            1 * authTokenService.updateCookieHeaderForAccessToken(request)
            0 * _

        and:
            response.statusCode == HttpStatus.NO_CONTENT
    }

    def "Should delegate call to service in order to invalidate refresh token"() {
        given:
            HttpServletRequest request = Mock()

        when:
            def response = authTokenController.invalidateRefreshToken(request)

        then:
            1 * authTokenService.invalidateRefreshToken(request)
            0 * _

        and:
            response.statusCode == HttpStatus.NO_CONTENT
    }

    def "Should delegate call to service in order to invalidate all refresh tokens of the user"() {
        given:
            HttpServletRequest request = Mock()

        when:
            def response = authTokenController.invalidateRefreshTokensByUsername(request)

        then:
            1 * authTokenService.invalidateRefreshTokensByUsername(request)
            0 * _

        and:
            response.statusCode == HttpStatus.NO_CONTENT
    }
}
