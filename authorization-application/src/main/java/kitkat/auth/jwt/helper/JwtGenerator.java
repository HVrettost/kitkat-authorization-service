package kitkat.auth.jwt.helper;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import kitkat.auth.config.properties.JWTConfigProperties;
import kitkat.auth.enumeration.CustomJwtClaims;
import kitkat.auth.util.DateUtils;

@Component
public class JwtGenerator {

    private final JWTConfigProperties jwtConfigProperties;
    private final DateUtils dateUtils;

    public JwtGenerator(JWTConfigProperties jwtConfigProperties, DateUtils dateUtils) {
        this.jwtConfigProperties = jwtConfigProperties;
        this.dateUtils = dateUtils;
    }

    public String generateAccessToken(String username, String permissions) {
        return JWT.create()
                .withIssuer(jwtConfigProperties.getIssuer())
                .withIssuedAt(dateUtils.getCurrentUTCDate())
                .withExpiresAt(dateUtils.getCurrentUTCDateWithOffset(jwtConfigProperties.getAccessTokenExpirationIntervalInMillis()))
                .withSubject(username)
                .withClaim(CustomJwtClaims.AUTHORITIES.getValue(), permissions)
                .sign(Algorithm.HMAC256(jwtConfigProperties.getSecret()));
    }

    public String generateRefreshToken(String username) {
        return JWT.create()
                .withIssuer(jwtConfigProperties.getIssuer())
                .withIssuedAt(dateUtils.getCurrentUTCDate())
                .withExpiresAt(dateUtils.getCurrentUTCDateWithOffset(jwtConfigProperties.getRefreshTokenExpirationIntervalInMillis()))
                .withSubject(username)
                .sign(Algorithm.HMAC256(jwtConfigProperties.getSecret()));
    }
}
