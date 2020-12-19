package kitkat.auth.mapper

import spock.lang.Specification

class RefreshTokenWhitelistMapperSpec extends Specification {

    RefreshTokenWhitelistMapper refreshTokenWhitelistMapper

    def setup() {
        refreshTokenWhitelistMapper = new RefreshTokenWhitelistMapper()
    }

    def "Should map properties to RefreshTokenWhitelist object"() {
        given:
            def refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            def username = "username"
            def userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"

        when:
            def response = refreshTokenWhitelistMapper.toEntity(refreshToken, username, userAgent)

        then:
            with(response) {
                it.username == username
                it.refreshToken == refreshToken
                it.userAgent == userAgent
            }
    }
}
