package kitkat.auth.jwt.factory

import com.auth0.jwt.interfaces.DecodedJWT
import kitkat.auth.config.properties.JWTConfigProperties
import spock.lang.Specification

class JwtVerifierFactorySpec extends Specification {

    JWTConfigProperties jwtConfigProperties
    JwtVerifierFactory jwtVerifierFactory

    def setup() {
        jwtConfigProperties = Mock(JWTConfigProperties) {
            1 * getSecret() >> "secret"
            1 * getIssuer() >> "issuer"
            1 * getLeeway() >> 5
        }
        jwtVerifierFactory = new JwtVerifierFactory(jwtConfigProperties)
    }

    def "Should create a jwt verifier"() {
        given:
            DecodedJWT decodedJWT = Mock()
            def subject = "username"

        when:
            def response = jwtVerifierFactory.getJwtVerifier(decodedJWT)

        then:
            1 * decodedJWT.subject >> subject

        and:
            with(response) {
                algorithm.name == "HS256"
                claims['iss'][0] == "issuer"
                claims['sub'] == subject
                claims['nbf'] == 5
            }
    }
}
