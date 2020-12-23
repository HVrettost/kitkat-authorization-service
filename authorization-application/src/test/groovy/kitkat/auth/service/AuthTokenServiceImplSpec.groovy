package kitkat.auth.service

import kitkat.auth.exception.AuthorizationException
import kitkat.auth.exception.error.AuthorizationError

import javax.servlet.http.HttpServletRequest

import kitkat.auth.dao.AuthRoleToPermissionsDao
import kitkat.auth.dao.AuthRoleToUsernameDao
import kitkat.auth.jwt.helper.JwtGenerator
import kitkat.auth.jwt.util.JwtClaimUtils
import kitkat.auth.model.dto.AuthRoleToAuthoritiesDto
import kitkat.auth.model.dto.AuthRoleToUsernameDto
import kitkat.auth.util.HeaderUtils
import spock.lang.Specification

import kitkat.auth.dao.RefreshTokenWhitelistDao
import kitkat.auth.util.CookieUtils

class AuthTokenServiceImplSpec extends Specification {

    RefreshTokenWhitelistDao refreshTokenWhitelistDao
    JwtClaimUtils jwtClaimUtils
    JwtGenerator jwtGenerator
    AuthRoleToUsernameDao authRoleToUsernameDao
    AuthRoleToPermissionsDao authRoleToPermissionsDao
    CookieUtils cookieUtils
    HeaderUtils headerUtils
    AuthTokenService authTokenService

    def setup() {
        refreshTokenWhitelistDao = Mock()
        jwtClaimUtils = Mock()
        jwtGenerator = Mock()
        authRoleToUsernameDao = Mock()
        authRoleToPermissionsDao = Mock()
        cookieUtils = Mock()
        headerUtils = Mock()
        authTokenService = new AuthTokenServiceImpl(refreshTokenWhitelistDao, jwtClaimUtils, jwtGenerator, authRoleToUsernameDao, authRoleToPermissionsDao, cookieUtils, headerUtils)
    }

    def "Should generate access token and refresh token and put them in cookie"() {
        given:
            def userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"
            def permissions = "PERMISSION1 PERMISSION2 PERMISSION3"
            def refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            def accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYXV0aHMiOiJQRVJNSVNTSU9OMSBQRVJNSVNTSU9OMiIsImlhdCI6MTUxNjIzOTAyMn0.cboyQI7SmetOrcqmdhxT69PdqcSmBDmWGuMft0b2Lrg"
            def username = "username"
            def authRole = "ROLE"
            def accessTokenCookie = "Access Token Cookie"
            def refreshTokenCookie = "Refresh Token Cookie"
            AuthRoleToAuthoritiesDto authRoleToAuthoritiesDto = Mock()
            AuthRoleToUsernameDto authRoleToUsernameDto = Mock()
            HttpServletRequest httpServletRequest = Mock()

        when:
            def response = authTokenService.createCookieHeadersForAuthorization(httpServletRequest, username)

        then: 'user agent is extracted from the http request'
            1 * headerUtils.extractUserAgent(httpServletRequest) >> userAgent

        and: 'permissions are retrieved for the user'
            1 * authRoleToUsernameDao.getAuthRoleByUsername(username) >> authRoleToUsernameDto
            1 * authRoleToUsernameDto.role >> authRole
            1 * authRoleToPermissionsDao.getPermissionsByAuthRole(authRole) >> authRoleToAuthoritiesDto
            1 * authRoleToAuthoritiesDto.authorities >> permissions

        and: 'access token and refresh token are generated'
            1 * jwtGenerator.generateAccessToken(username, permissions) >> accessToken
            1 * jwtGenerator.generateRefreshToken(username) >> refreshToken

        and: 'invalidate existing refresh token and create new'
            1 * refreshTokenWhitelistDao.isRefreshTokenExistsByUsernameAndUserAgent(username, userAgent) >> true
            1 * refreshTokenWhitelistDao.invalidateRefreshToken(username, userAgent)
            1 * refreshTokenWhitelistDao.saveRefreshToken(refreshToken, username, userAgent)

        and: 'cookies are created to be passed in the http headers'
            1 * cookieUtils.createAccessTokenCookie(accessToken) >> accessTokenCookie
            1 * cookieUtils.createRefreshTokenCookie(refreshToken) >> refreshTokenCookie
            0 * _

        and:
            with(response['Set-Cookie']) {
                contains(accessTokenCookie)
                contains(refreshTokenCookie)
            }
    }

    def "Should generate access token and refresh token and put them in cookie without invalidating existing tokens"() {
        given:
            def userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"
            def permissions = "PERMISSION1 PERMISSION2 PERMISSION3"
            def refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            def accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYXV0aHMiOiJQRVJNSVNTSU9OMSBQRVJNSVNTSU9OMiIsImlhdCI6MTUxNjIzOTAyMn0.cboyQI7SmetOrcqmdhxT69PdqcSmBDmWGuMft0b2Lrg"
            def username = "username"
            def authRole = "ROLE"
            def accessTokenCookie = "Access Token Cookie"
            def refreshTokenCookie = "Refresh Token Cookie"
            AuthRoleToAuthoritiesDto authRoleToAuthoritiesDto = Mock()
            AuthRoleToUsernameDto authRoleToUsernameDto = Mock()
            HttpServletRequest httpServletRequest = Mock()

        when:
            def response = authTokenService.createCookieHeadersForAuthorization(httpServletRequest, username)

        then: 'user agent is extracted from the http request'
            1 * headerUtils.extractUserAgent(httpServletRequest) >> userAgent

        and: 'permissions are retrieved for the user'
            1 * authRoleToUsernameDao.getAuthRoleByUsername(username) >> authRoleToUsernameDto
            1 * authRoleToUsernameDto.role >> authRole
            1 * authRoleToPermissionsDao.getPermissionsByAuthRole(authRole) >> authRoleToAuthoritiesDto
            1 * authRoleToAuthoritiesDto.authorities >> permissions

        and: 'access token and refresh token are generated'
            1 * jwtGenerator.generateAccessToken(username, permissions) >> accessToken
            1 * jwtGenerator.generateRefreshToken(username) >> refreshToken

        and: 'create new token in database'
            1 * refreshTokenWhitelistDao.isRefreshTokenExistsByUsernameAndUserAgent(username, userAgent) >> false
            1 * refreshTokenWhitelistDao.saveRefreshToken(refreshToken, username, userAgent)

        and: 'cookies are created to be passed in the http headers'
            1 * cookieUtils.createAccessTokenCookie(accessToken) >> accessTokenCookie
            1 * cookieUtils.createRefreshTokenCookie(refreshToken) >> refreshTokenCookie
            0 * _

        and:
            with(response['Set-Cookie']) {
                contains(accessTokenCookie)
                contains(refreshTokenCookie)
            }
    }

    def "Should update access token with refresh token cookie"() {
        given:
            def permissions = "PERMISSION1 PERMISSION2 PERMISSION3"
            def refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            def accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYXV0aHMiOiJQRVJNSVNTSU9OMSBQRVJNSVNTSU9OMiIsImlhdCI6MTUxNjIzOTAyMn0.cboyQI7SmetOrcqmdhxT69PdqcSmBDmWGuMft0b2Lrg"
            def username = "username"
            def authRole = "ROLE"
            def accessTokenCookie = "Access Token Cookie"
            def refreshTokenCookie = "Refresh Token Cookie"
            AuthRoleToAuthoritiesDto authRoleToAuthoritiesDto = Mock()
            AuthRoleToUsernameDto authRoleToUsernameDto = Mock()
            HttpServletRequest httpServletRequest = Mock()

        when:
            def response = authTokenService.updateCookieHeaderForAccessToken(httpServletRequest)

        then: 'extract information from http request'
            1 * headerUtils.extractRefreshToken(httpServletRequest) >> refreshToken
            1 * jwtClaimUtils.extractSubjectClaim(refreshToken) >> username

        and: 'permissions are retrieved for the user'
            1 * authRoleToUsernameDao.getAuthRoleByUsername(username) >> authRoleToUsernameDto
            1 * authRoleToUsernameDto.role >> authRole
            1 * authRoleToPermissionsDao.getPermissionsByAuthRole(authRole) >> authRoleToAuthoritiesDto
            1 * authRoleToAuthoritiesDto.authorities >> permissions

        and: 'access token and refresh token are generated'
            1 * jwtGenerator.generateAccessToken(username, permissions) >> accessToken

        and: 'cookies are created to be passed in the http headers'
            1 * cookieUtils.createAccessTokenCookie(accessToken) >> accessTokenCookie
            1 * cookieUtils.createRefreshTokenCookie(refreshToken) >> refreshTokenCookie
            0 * _

        and:
            with(response['Set-Cookie']) {
                contains(accessTokenCookie)
                contains(refreshTokenCookie)
            }
    }

    def "Should invalidate refresh token by username and user agent successfully"() {
        given:
            def accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            def userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"
            def username = "username"
            HttpServletRequest httpServletRequest = Mock()

        when:
            authTokenService.invalidateRefreshToken(httpServletRequest)

        then: 'extract information from http request'
            1 * headerUtils.extractAccessToken(httpServletRequest) >> accessToken
            1 * headerUtils.extractUserAgent(httpServletRequest) >> userAgent
            1 * jwtClaimUtils.extractSubjectClaim(accessToken) >> username

        and: 'check that refresh token exists for the username and user agent'
            1 * refreshTokenWhitelistDao.isRefreshTokenExistsByUsernameAndUserAgent(username, userAgent) >> true

        and: 'invalidate refresh token'
            1 * refreshTokenWhitelistDao.invalidateRefreshToken(username, userAgent)
            0 * _
    }

    def "Should invalidate all user's refresh tokens"() {
        given:
            def accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            def username = "username"
            HttpServletRequest httpServletRequest = Mock()

        when:
            authTokenService.invalidateRefreshTokensByUsername(httpServletRequest)

        then: 'extract information from http request'
            1 * headerUtils.extractAccessToken(httpServletRequest) >> accessToken
            1 * jwtClaimUtils.extractSubjectClaim(accessToken) >> username

        and: 'check that refresh token exists for the username and user agent'
            1 * refreshTokenWhitelistDao.isRefreshTokenExistsByUsername(username) >> true

        and: 'invalidate refresh token'
            1 * refreshTokenWhitelistDao.invalidateRefreshTokensByUsername(username)
            0 * _
    }

    def "Should throw AuthorizationException if refresh token by username and user agent exists"() {
        given:
            def accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            def userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"
            def username = "username"
            HttpServletRequest httpServletRequest = Mock()

        when:
            authTokenService.invalidateRefreshToken(httpServletRequest)

        then: 'extract information from http request'
            1 * headerUtils.extractAccessToken(httpServletRequest) >> accessToken
            1 * headerUtils.extractUserAgent(httpServletRequest) >> userAgent
            1 * jwtClaimUtils.extractSubjectClaim(accessToken) >> username

        and: 'check that refresh token does not exist'
            1 * refreshTokenWhitelistDao.isRefreshTokenExistsByUsernameAndUserAgent(username, userAgent) >> false
            0 * _

        and: 'AuthorizationException is thrown'
            AuthorizationException authEx = thrown()
            with(authEx) {
                errorDetails.message == AuthorizationError.REFRESH_TOKEN_NOT_FOUND.message
                errorDetails.errorCode == AuthorizationError.REFRESH_TOKEN_NOT_FOUND.errorCode
                description == AuthorizationError.REFRESH_TOKEN_NOT_FOUND.description
            }
    }

    def "Should throw AuthorizationException if refresh token by username do not exist"() {
        given:
            def accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            def username = "username"
            HttpServletRequest httpServletRequest = Mock()

        when:
            authTokenService.invalidateRefreshTokensByUsername(httpServletRequest)

        then: 'extract information from http request'
            1 * headerUtils.extractAccessToken(httpServletRequest) >> accessToken
            1 * jwtClaimUtils.extractSubjectClaim(accessToken) >> username

        and: 'check that refresh token does not exist'
            1 * refreshTokenWhitelistDao.isRefreshTokenExistsByUsername(username) >> false
            0 * _

        and: 'AuthorizationException is thrown'
            AuthorizationException authEx = thrown()
            with(authEx) {
                errorDetails.message == AuthorizationError.REFRESH_TOKENS_NOT_FOUND.message
                errorDetails.errorCode == AuthorizationError.REFRESH_TOKENS_NOT_FOUND.errorCode
                description == AuthorizationError.REFRESH_TOKENS_NOT_FOUND.description
            }
    }
}
