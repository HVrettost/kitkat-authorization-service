package kitkat.auth.jwt.validator

import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.DecodedJWT
import kitkat.auth.exception.AuthorizationException
import kitkat.auth.exception.error.AuthorizationError
import kitkat.auth.jwt.factory.JwtVerifierFactory
import spock.lang.Specification
import spock.lang.Unroll

class JwtValidatorSpec extends Specification {

    JwtVerifierFactory jwtVerifierFactory
    JwtValidator jwtValidator

    def setup() {
        jwtVerifierFactory = Mock()
        jwtValidator = new JwtValidator(jwtVerifierFactory)
    }

    def "Should decode successfully jwt token"() {
        given:
            def token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9' +
                    '.eyJzdWIiOiJ1c2VybmFtZSIsImF1dGhzIjoiUkVGUkVTSF9UT0tFTl9ERUxFVEUgUkVGUkVTSF9UT0tFTl9BTExfREVMRVRFIiwiaXNzIjoiZDIwYjcxZTgwMzVkNGYzMDBhZmRlMDEzYTZlOGRkOGQ3ZTg5NTE2OTVmZTk2NTg2NTg4ODQ5MzRlYzM1NTBlNyIsImV4cCI6MTYwODMwNDIwOCwiaWF0IjoxNjA4MzAzOTA4fQ' +
                    '.5WEQoXpENDM7VxkCJu0YUrcLS-zOtItfane-CWTlACk'

        when:
            jwtValidator.validate(token)

        then:
            1 * jwtVerifierFactory.getJwtVerifier(_ as DecodedJWT)
            0 * _
    }

    def "Should throw AuthorizationException if token has expired"() {
        given:
            def token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9' +
                    '.eyJzdWIiOiJ1c2VybmFtZSIsImF1dGhzIjoiUkVGUkVTSF9UT0tFTl9ERUxFVEUgUkVGUkVTSF9UT0tFTl9BTExfREVMRVRFIiwiaXNzIjoiZDIwYjcxZTgwMzVkNGYzMDBhZmRlMDEzYTZlOGRkOGQ3ZTg5NTE2OTVmZTk2NTg2NTg4ODQ5MzRlYzM1NTBlNyIsImV4cCI6MTYwODMwNDIwOCwiaWF0IjoxNjA4MzAzOTA4fQ' +
                    '.5WEQoXpENDM7VxkCJu0YUrcLS-zOtItfane-CWTlACk'

        when:
            jwtValidator.validate(token)

        then: 'when the verifier throws a TokenExpiredException'
            1 * jwtVerifierFactory.getJwtVerifier(_ as DecodedJWT) >> { throw new TokenExpiredException('token expired message') }
            0 * _

        and:
            AuthorizationException authEx = thrown()
            with(authEx) {
                errorDetails.message == AuthorizationError.TOKEN_EXPIRED.message
                errorDetails.errorCode == AuthorizationError.TOKEN_EXPIRED.errorCode
            }
    }

    @Unroll
    def "Should throw AuthorizationException if verifier fails to verify token"() {
        given:
            def token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9' +
                    '.eyJzdWIiOiJ1c2VybmFtZSIsImF1dGhzIjoiUkVGUkVTSF9UT0tFTl9ERUxFVEUgUkVGUkVTSF9UT0tFTl9BTExfREVMRVRFIiwiaXNzIjoiZDIwYjcxZTgwMzVkNGYzMDBhZmRlMDEzYTZlOGRkOGQ3ZTg5NTE2OTVmZTk2NTg2NTg4ODQ5MzRlYzM1NTBlNyIsImV4cCI6MTYwODMwNDIwOCwiaWF0IjoxNjA4MzAzOTA4fQ' +
                    '.5WEQoXpENDM7VxkCJu0YUrcLS-zOtItfane-CWTlACk'

        when:
            jwtValidator.validate(token)

        then: 'when the verifier throws #exception'
            1 * jwtVerifierFactory.getJwtVerifier(_ as DecodedJWT) >> { throw exception }
            0 * _

        and:
            AuthorizationException authEx = thrown()
            with(authEx) {
                errorDetails.message == AuthorizationError.INVALID_TOKEN.message
                errorDetails.errorCode == AuthorizationError.INVALID_TOKEN.errorCode
            }

        where:
            exception << [new JWTDecodeException('decode failed message'),
                          new JWTVerificationException('verification failed message')]
    }
}
