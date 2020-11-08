package kitkat.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import kitkat.auth.config.properties.JWTConfigProperties;
import kitkat.auth.enumeration.Claims;
import kitkat.auth.model.dto.AuthTokenDto;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private final JWTConfigProperties jwtConfigProperties;
    private final DateUtils dateUtils;

    public JwtUtils(JWTConfigProperties jwtConfigProperties,
                    DateUtils dateUtils) {
        this.jwtConfigProperties = jwtConfigProperties;
        this.dateUtils = dateUtils;
    }

    public String generateAccessToken(String username, String permissions) {
        return JWT.create()
                .withIssuer(jwtConfigProperties.getIssuer())
                .withIssuedAt(dateUtils.getCurrentUTCDate())
                .withExpiresAt(dateUtils.getCurrentUTCDateWithOffset(jwtConfigProperties.getAccessTokenExpirationIntervalInMillis()))
                .withSubject(username)
                .withClaim(Claims.PERMISSIONS.getValue(), permissions)
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

    public void validateToken(String token) {
        DecodedJWT decodedToken = JWT.decode(token);
        getJWTVerifier(decodedToken).verify(token);
    }

    private JWTVerifier getJWTVerifier(DecodedJWT token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtConfigProperties.getSecret());
        return JWT.require(algorithm)
                .withIssuer(jwtConfigProperties.getIssuer())
                .withSubject(token.getSubject())
                .acceptLeeway(jwtConfigProperties.getLeeway())
                .build();
    }

    public String extractPermissionsClaim(String accessToken) {
        DecodedJWT decodedToken = JWT.decode(accessToken);
        return decodedToken.getClaim(Claims.PERMISSIONS.getValue()).asString();
    }
}

