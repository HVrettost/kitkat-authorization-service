package kitkat.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import kitkat.auth.config.properties.JWTConfigProperties;
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

    public String generateAccessToken(String username) {
        return JWT.create()
                .withIssuer(jwtConfigProperties.getIssuer())
                .withIssuedAt(dateUtils.getCurrentUTCDate())
                .withExpiresAt(dateUtils.getCurrentUTCDateWithOffset(jwtConfigProperties.getAccessTokenExpirationIntervalInMillis()))
                .withSubject(username)
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

