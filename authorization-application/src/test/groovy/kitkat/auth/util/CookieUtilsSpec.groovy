package kitkat.auth.util

import spock.lang.Specification

class CookieUtilsSpec extends Specification {

    CookieUtils cookieUtils

    def setup() {
        cookieUtils = new CookieUtils()
    }

    def "Should create access token cookie"() {
        given:
            def accessToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'

        when:
            def response = cookieUtils.createAccessTokenCookie(accessToken)

        then:
            0 * _

        and:
            response == 'accessToken=' + accessToken + '; Path=api/auth/token; HttpOnly; Domain=localhost; SameSite=None; Secure'
    }

    def "Should create refresh token cookie"() {
        given:
            def refreshToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'

        when:
            def response = cookieUtils.createAccessTokenCookie(refreshToken)

        then:
            0 * _

        and:
            response == 'accessToken=' + refreshToken + '; Path=api/auth/token; HttpOnly; Domain=localhost; SameSite=None; Secure'
    }
}
