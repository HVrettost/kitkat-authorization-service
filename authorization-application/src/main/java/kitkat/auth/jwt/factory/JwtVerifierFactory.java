package kitkat.auth.jwt.factory;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import kitkat.auth.config.properties.JWTConfigProperties;

@Component
public class JwtVerifierFactory {

    private final JWTConfigProperties jwtConfigProperties;

    public JwtVerifierFactory(JWTConfigProperties jwtConfigProperties) {
        this.jwtConfigProperties = jwtConfigProperties;
    }

    public JWTVerifier getJwtVerifier(DecodedJWT decodedJWT) {
        Algorithm algorithm = Algorithm.HMAC256(jwtConfigProperties.getSecret());
        return JWT.require(algorithm)
                .withIssuer(jwtConfigProperties.getIssuer())
                .withSubject(decodedJWT.getSubject())
                .acceptLeeway(jwtConfigProperties.getLeeway())
                .build();
    }
}
