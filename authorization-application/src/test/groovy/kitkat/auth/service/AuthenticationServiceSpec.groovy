package kitkat.auth.service

import kitkat.auth.exception.AuthorizationException
import kitkat.auth.exception.error.AuthError
import kitkat.auth.model.dto.AuthenticationRequestDto
import kitkat.auth.util.CookieUtils
import kitkat.auth.util.JwtUtils
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Specification

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationServiceSpec extends Specification {

    AuthenticationManager authenticationManager
    AuthTokenService authTokenService
    JwtUtils jwtUtils
    CookieUtils cookieUtils
    AuthenticationService authenticationService

    def setup() {
        authenticationManager = Mock()
        authTokenService = Mock()
        jwtUtils = Mock()
        cookieUtils = Mock()
        authenticationService = new AuthenticationServiceImpl(authenticationManager, authTokenService, jwtUtils, cookieUtils)
    }

    def "Should authenticate user successfully and add access token and refresh token as cookies"() {
        given:
            HttpServletRequest httpServletRequest = Mock()
            HttpServletResponse httpServletResponse = Mock()
            AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto("username", "password")
            def permissions = "INVALIDATE_TOKEN UPDATE_REFRESH_TOKEN"
            def accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            def refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWFsdadadwyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            def userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"
            Cookie accessTokenCookie = Mock()
            Cookie refreshTokenCookie = Mock()

        when:
            authenticationService.authenticate(httpServletRequest, httpServletResponse, authenticationRequestDto)

        then:
            1 * authenticationManager.authenticate(_ as UsernamePasswordAuthenticationToken)
            1 * httpServletRequest.getHeader(HttpHeaders.USER_AGENT) >> userAgent
            1 * authTokenService.getUserPermissions(authenticationRequestDto.username) >> permissions
            1 * jwtUtils.generateAccessToken(authenticationRequestDto.username, permissions) >> accessToken
            1 * jwtUtils.generateRefreshToken(authenticationRequestDto.username) >> refreshToken
            1 * authTokenService.invalidateRefreshTokenIfExistsAndSaveNew(refreshToken, authenticationRequestDto.username, _ as String)
            1 * cookieUtils.createAccessTokenCookie(accessToken) >> accessTokenCookie
            1 * cookieUtils.createRefreshTokenCookie(refreshToken) >> refreshTokenCookie
            1 * httpServletResponse.addCookie(accessTokenCookie)
            1 * httpServletResponse.addCookie(refreshTokenCookie)
            0 * _
    }

    def "Should throw AuthorizationException if could not extract user agent from header"() {
        given:
            HttpServletRequest httpServletRequest = Mock()
            HttpServletResponse httpServletResponse = Mock()
            AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto("username", "password")

        when:
            authenticationService.authenticate(httpServletRequest, httpServletResponse, authenticationRequestDto)

        then:
            1 * authenticationManager.authenticate(_ as UsernamePasswordAuthenticationToken)
            1 * httpServletRequest.getHeader(HttpHeaders.USER_AGENT) >> null
            0 * _

        and:
            AuthorizationException authEx = thrown(AuthorizationException)
            with(authEx) {
                errorDetails.message == AuthError.USER_AGENT_NOT_FOUND.message
                errorDetails.errorCode == AuthError.USER_AGENT_NOT_FOUND.errorCode
                description == AuthError.USER_AGENT_NOT_FOUND.description
            }
    }
}
