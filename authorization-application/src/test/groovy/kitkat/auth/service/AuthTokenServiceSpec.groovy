package kitkat.auth.service

import spock.lang.Specification

import kitkat.auth.dao.RefreshTokenWhitelistDao
import kitkat.auth.util.CookieUtils
import kitkat.auth.util.JwtUtils

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthTokenServiceSpec extends Specification {

    RefreshTokenWhitelistDao refreshTokenWhitelistDao
    JwtUtils jwtUtils
    AuthRoleService authRoleService
    CookieUtils cookieUtils
    AuthTokenService authTokenService

    def setup() {
        refreshTokenWhitelistDao = Mock()
        jwtUtils = Mock()
        authRoleService = Mock()
        cookieUtils = Mock()
        authTokenService = new AuthTokenServiceImpl(refreshTokenWhitelistDao, jwtUtils, authRoleService, cookieUtils)
    }

    def "Should save user's refresh token by user agent and delegate call to database to delete it"() {
        given:
            def refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            def username = "username"
            def userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"

        when:
            authTokenService.invalidateRefreshTokenIfExistsAndSaveNew(refreshToken, username, userAgent)

        then:
            1 * refreshTokenWhitelistDao.countTokensByUsernameAndUserAgent(username, userAgent) >> 2
            1 * refreshTokenWhitelistDao.invalidateRefreshToken(username, userAgent)
            1 * refreshTokenWhitelistDao.saveRefreshToken(refreshToken, username, userAgent)
            0 * _
    }

    def "Should save user's refresh token by user agent and delegate call to database to just save it as it does not exists in database"() {
        given:
            def refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            def username = "username"
            def userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"

        when:
            authTokenService.invalidateRefreshTokenIfExistsAndSaveNew(refreshToken, username, userAgent)

        then:
            1 * refreshTokenWhitelistDao.countTokensByUsernameAndUserAgent(username, userAgent) >> 0
            1 * refreshTokenWhitelistDao.saveRefreshToken(refreshToken, username, userAgent)
            0 * _
    }

    /*ef "Should update successfully access token"() {
        given:
            HttpServletRequest httpServletRequest = Mock()
            HttpServletResponse httpServletResponse = Mock()

        when:


    }*/
}
