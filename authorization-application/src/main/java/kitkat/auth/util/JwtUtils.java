package kitkat.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import kitkat.auth.config.properties.JWTConfigProperties;
import kitkat.auth.model.AuthenticationRequestDto;
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

    public String generateAccessToken(AuthenticationRequestDto authenticationRequest) {
        return JWT.create()
                .withIssuer(jwtConfigProperties.getIssuer())
                .withIssuedAt(dateUtils.getCurrentDate())
                .withExpiresAt(dateUtils.getCurrentDateWithOffset(jwtConfigProperties.getAccessTokenExpirationIntervalInMillis()))
                .withSubject(authenticationRequest.getUsername())
                .sign(Algorithm.HMAC256(jwtConfigProperties.getSecret()));
    }

    public String generateRefreshToken(AuthenticationRequestDto authenticationRequest) {
        return JWT.create()
                .withIssuer(jwtConfigProperties.getIssuer())
                .withIssuedAt(dateUtils.getCurrentDate())
                .withExpiresAt(dateUtils.getCurrentDateWithOffset(jwtConfigProperties.getRefreshTokenExpirationIntervalInMillis()))
                .withSubject(authenticationRequest.getUsername())
                .sign(Algorithm.HMAC256(jwtConfigProperties.getSecret()));
    }
}

