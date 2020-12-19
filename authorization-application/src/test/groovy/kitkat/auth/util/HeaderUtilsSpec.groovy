package kitkat.auth.util

import kitkat.auth.exception.AuthorizationException
import kitkat.auth.exception.error.AuthError
import kitkat.auth.trait.CookieHelper
import kitkat.auth.validator.TokenCookieValidator
import org.springframework.http.HttpHeaders
import spock.lang.Specification
import spock.lang.Unroll

import javax.servlet.http.HttpServletRequest

class HeaderUtilsSpec extends Specification implements CookieHelper{

    TokenCookieValidator tokenCookieValidator
    HeaderUtils headerUtils

    def setup() {
        tokenCookieValidator = Mock()
        headerUtils = new HeaderUtils(tokenCookieValidator)
    }

    def "Should extract user agent from http request successfully"() {
        given:
            def userAgent = 'Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0'
            HttpServletRequest httpServletRequest = Mock()
            1 * httpServletRequest.getHeader(HttpHeaders.USER_AGENT) >> userAgent

        when:
            def response = headerUtils.extractUserAgent(httpServletRequest)

        then:
            response == userAgent
    }

    @Unroll
    def "Should throw AuthorizationException if user agent is empty or null"() {
        given:
            HttpServletRequest httpServletRequest = Mock()
            1 * httpServletRequest.getHeader(HttpHeaders.USER_AGENT) >> userAgent

        when:
            headerUtils.extractUserAgent(httpServletRequest)

        then:
            AuthorizationException authorizationException = thrown()
            with(authorizationException.errorDetails) {
                message == AuthError.USER_AGENT_NOT_FOUND.message
                errorCode == AuthError.USER_AGENT_NOT_FOUND.errorCode
            }

        where:
            userAgent << [null, ""]
    }

    def "Should extract access token from cookie"() {
        given:
            def cookie = createValidTokenCookie()
            HttpServletRequest httpServletRequest = Mock()
            1 * httpServletRequest.getHeader(HttpHeaders.COOKIE) >> cookie

        when:
            def response = headerUtils.extractAccessToken(httpServletRequest)

        then:
            1 * tokenCookieValidator.validate(cookie)

        and:
            response == 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYXV0aHMiOiJQRVJNSVNTSU9OMSBQRVJNSVNTSU9OMiIsImlhdCI6MTUxNjIzOTAyMn0.cboyQI7SmetOrcqmdhxT69PdqcSmBDmWGuMft0b2Lrg'
    }

    def "Should extract refresh token from cookie"() {
        given:
            def cookie = createValidTokenCookie()
            HttpServletRequest httpServletRequest = Mock()
            1 * httpServletRequest.getHeader(HttpHeaders.COOKIE) >> cookie

        when:
            def response = headerUtils.extractRefreshToken(httpServletRequest)

        then:
            1 * tokenCookieValidator.validate(cookie)

        and:
            response == 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'
    }
}
