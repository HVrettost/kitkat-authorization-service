package kitkat.auth.util

import kitkat.auth.config.properties.CookieConfigProperties
import spock.lang.Specification
import spock.lang.Unroll

class CookieUtilsSpec extends Specification {

    CookieConfigProperties cookieConfigProperties
    CookieUtils cookieUtils

    def setup() {
        cookieConfigProperties = new CookieConfigProperties(path: 'api/auth/token',
                        httpOnly: true,
                        domain: 'localhost',
                        sameSite: 'None',
                        secure: true)
        cookieUtils = new CookieUtils(cookieConfigProperties)
    }

    def "Should create access token cookie"() {
        given:
            def accessToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'

        when:
            def response = cookieUtils.createAccessTokenCookie(accessToken)

        then:
            0 * _

        and:
            response == 'accessToken=' + accessToken + '; Path=api/auth/token; HttpOnly; Secure; SameSite=None; Domain=localhost'
    }

    def "Should create refresh token cookie"() {
        given:
            def refreshToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'

        when:
            def response = cookieUtils.createRefreshTokenCookie(refreshToken)

        then:
            0 * _

        and:
            response == 'refreshToken=' + refreshToken + '; Path=api/auth/token; HttpOnly; Secure; SameSite=None; Domain=localhost'
    }

    @Unroll
    def "Should create access token cookie according to given cookie properties"() {
        given:
            def accessToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'
            cookieConfigProperties = new CookieConfigProperties(path: 'api/auth/token',
                    httpOnly: httpOnly,
                    domain: 'localhost',
                    sameSite: sameSite,
                    secure: secure)
            cookieUtils = new CookieUtils(cookieConfigProperties)

        when:
            def response = cookieUtils.createAccessTokenCookie(accessToken)

        then:
            0 * _

        and:
            response == 'accessToken=' + accessToken + '; Path=api/auth/token; ' +
            (httpOnly ? 'HttpOnly; ' : '') +
            (secure ? 'Secure; ' : '') +
            (sameSite ? 'SameSite=' + sameSite + '; ' : '') +
            'Domain=localhost'

        where:
            httpOnly | sameSite | secure
            true     | 'None'   | true
            false    | null     | false
            false    | ''       | false
            true     | 'None'   | false
            false    | 'None'   | true
    }


}
