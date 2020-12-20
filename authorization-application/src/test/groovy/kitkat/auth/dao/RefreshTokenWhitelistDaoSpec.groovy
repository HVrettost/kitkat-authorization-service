package kitkat.auth.dao

import spock.lang.Specification

import kitkat.auth.mapper.RefreshTokenWhitelistMapper
import kitkat.auth.model.entity.RefreshTokenWhitelist
import kitkat.auth.repository.AuthTokenRepository
import spock.lang.Unroll

class RefreshTokenWhitelistDaoSpec extends Specification {

    AuthTokenRepository authTokenRepository
    RefreshTokenWhitelistMapper refreshTokenWhitelistMapper
    RefreshTokenWhitelistDao refreshTokenWhitelistDao

    def setup() {
        authTokenRepository = Mock()
        refreshTokenWhitelistMapper = Mock()
        refreshTokenWhitelistDao = new RefreshTokenWhitelistDaoImpl(authTokenRepository, refreshTokenWhitelistMapper)
    }

    def "should save refresh token in database successfully"() {
        given:
            def refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            def username = "username"
            def userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"
            RefreshTokenWhitelist refreshTokenWhitelist = Mock()

        when:
            refreshTokenWhitelistDao.saveRefreshToken(refreshToken, username, userAgent)

        then:
            1 * refreshTokenWhitelistMapper.toEntity(refreshToken, username, userAgent) >> refreshTokenWhitelist
            1 * authTokenRepository.save(refreshTokenWhitelist)
            0 * _
    }

    def "Should invalidate refresh token successfully"() {
        given:
            def username = "username"
            def userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"

        when:
            refreshTokenWhitelistDao.invalidateRefreshToken(username, userAgent)

        then:
            1 * authTokenRepository.deleteByUsernameAndUserAgent(username, userAgent)
            0 * _
    }

    def "Should count user refresh tokens in database by user agent"() {
        given:
            def username = "username"
            def userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"

        when:
            def response = refreshTokenWhitelistDao.countTokensByUsernameAndUserAgent(username, userAgent)

        then:
            1 * authTokenRepository.countByUsernameAndUserAgent(username, userAgent) >> 3
            0 * _

        and:
            response == 3
    }

    def "Should invalidate all user's refresh tokens successfully"() {
        given:
            def username = "username"

        when:
            refreshTokenWhitelistDao.invalidateRefreshTokensByUsername(username)

        then:
            1 * authTokenRepository.deleteAllByUsername(username)
            0 * _
    }

    @Unroll
    def "Check if user has any refresh tokens in database"() {
        given:
            def username = "username"

        when:
            def response = refreshTokenWhitelistDao.isRefreshTokenExistsByUsername(username)

        then:
            1 * authTokenRepository.existsByUsername(username) >> existence
            0 * _

        and:
            response == existence

        where:
            existence << [true, false]
    }

    @Unroll
    def "Check if user has any refresh tokens in database given a user agent"() {
        given:
            def username = "username"
            def userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"

        when:
            def response = refreshTokenWhitelistDao.isRefreshTokenExistsByUsernameAndUserAgent(username, userAgent)

        then:
            1 * authTokenRepository.existsByUsernameAndUserAgent(username, userAgent) >> existence
            0 * _

        and:
            response == existence

        where:
            existence << [true, false]
    }
}
