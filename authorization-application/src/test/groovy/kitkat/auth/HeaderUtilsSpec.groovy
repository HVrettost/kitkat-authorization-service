package kitkat.auth

import kitkat.auth.exception.AuthorizationException
import kitkat.auth.exception.error.AuthError
import kitkat.auth.util.HeaderUtils
import org.springframework.http.HttpHeaders
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class HeaderUtilsSpec extends Specification{

    HeaderUtils headerUtils
    String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImF1dGhzIjoiUkVGUkVTSF9UT0tFTl9ERUxFVEUgUkVGUkVTSF9UT0tFTl9BTExfREVMRVRFIiwiaXNzIjoiZDIwYjcxZTgwMzVkNGYzMDBhZmRlMDEzYTZlOGRkOGQ3ZTg5NTE2OTVmZTk2NTg2NTg4ODQ5MzRlYzM1NTBlNyIsImV4cCI6MTYwNzI2MjAxMywiaWF0IjoxNjA3MjYxNzEzfQ.N-a5YkQIGimSij5QZBOuPx8wnVjyfoDuDqb70xCkUiY"
    String refreshToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlzcyI6ImQyMGI3MWU4MDM1ZDRmMzAwYWZkZTAxM2E2ZThkZDhkN2U4OTUxNjk1ZmU5NjU4NjU4ODg0OTM0ZWMzNTUwZTciLCJleHAiOjE2MDcyNjM1MTMsImlhdCI6MTYwNzI2MTcxM30.sXMKRhl_g_yrwc7Ogj-Iyt4Dv2049LfbWallEkJvho8"

    def setup() {
        headerUtils = new HeaderUtils()
    }

    def "Should extract from Cookie header accessToken successfully"() {
        given:
            HttpServletRequest request = Mock()

        when:
            def response = headerUtils.extractAccessToken(request)

        then:
            1 * request.getHeader(HttpHeaders.COOKIE) >> "accessToken=" + accessToken + "; refreshToken=" + refreshToken
            0 * _

        and:
            response == accessToken
    }

    def "Should throw AuthorizationException if value from cookie header is null when trying to extract access token"() {
        given:
            HttpServletRequest request = Mock()

        when:
            headerUtils.extractAccessToken(request)

        then:
            1 * request.getHeader(HttpHeaders.COOKIE) >> null
            0 * _

        and:
            AuthorizationException ex = thrown(AuthorizationException)
            with(ex.errorDetails) {
                errorCode == AuthError.TOKEN_COULD_NOT_BE_EXTRACTED.errorCode
                message == AuthError.TOKEN_COULD_NOT_BE_EXTRACTED.message
            }
    }

    def "Should extract from Cookie header refreshToken successfully"() {
        given:
            HttpServletRequest request = Mock()

        when:
            def response = headerUtils.extractRefreshToken(request)

        then:
            1 * request.getHeader(HttpHeaders.COOKIE) >> "accessToken=" + accessToken + "; refreshToken=" + refreshToken
            0 * _

        and:
            response == refreshToken
    }

    def "Should throw AuthorizationException if value from cookie header is null when trying to extract refresh token"() {
        given:
            HttpServletRequest request = Mock()

        when:
            headerUtils.extractRefreshToken(request)

        then:
            1 * request.getHeader(HttpHeaders.COOKIE) >> null
            0 * _

        and:
            AuthorizationException ex = thrown(AuthorizationException)
            with(ex.errorDetails) {
                errorCode == AuthError.TOKEN_COULD_NOT_BE_EXTRACTED.errorCode
                message == AuthError.TOKEN_COULD_NOT_BE_EXTRACTED.message
            }
    }


}
