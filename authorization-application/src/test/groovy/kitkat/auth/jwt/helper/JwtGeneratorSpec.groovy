package kitkat.auth.jwt.helper

import kitkat.auth.config.properties.JWTConfigProperties
import kitkat.auth.util.DateUtils
import spock.lang.Specification

class JwtGeneratorSpec extends Specification {

    JWTConfigProperties jwtConfigProperties
    DateUtils dateUtils
    JwtGenerator jwtGenerator

    def setup() {
        jwtConfigProperties = Mock()
        dateUtils = Mock()
        jwtGenerator = new JwtGenerator(jwtConfigProperties, dateUtils)
    }

    def "Should generate access token"() {
        given:
            def username = "username"
            def permissions = "DELETE_TOKEN CREATE_TOKEN"
            def currentDate = new Date()
            def offset = 10000L

        when:
            def response = jwtGenerator.generateAccessToken(username, permissions)

        then: 'jwt config properties is called to set properties for jwt token'
            1 * jwtConfigProperties.issuer >> "issuer"
            1 * jwtConfigProperties.accessTokenExpirationIntervalInMillis >> offset
            1 * jwtConfigProperties.secret >> "secret"

        and: 'date properties are set for the jwt token'
            1 * dateUtils.getCurrentUTCDate() >> currentDate
            1 * dateUtils.getCurrentUTCDateWithOffset(offset)
            0 * _

        and:
            response
    }

    def "Should generate refresh token"() {
        given:
            def username = "username"
            def currentDate = new Date()
            def offset = 10000L

        when:
            def response = jwtGenerator.generateRefreshToken(username)

        then: 'jwt config properties is called to set properties for jwt token'
            1 * jwtConfigProperties.issuer >> "issuer"
            1 * jwtConfigProperties.refreshTokenExpirationIntervalInMillis >> offset
            1 * jwtConfigProperties.secret >> "secret"

        and: 'date properties are set for the jwt token'
            1 * dateUtils.getCurrentUTCDate() >> currentDate
            1 * dateUtils.getCurrentUTCDateWithOffset(offset)
            0 * _

        and:
            response
    }
}
