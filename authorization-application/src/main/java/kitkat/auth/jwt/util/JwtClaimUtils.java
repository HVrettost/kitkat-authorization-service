package kitkat.auth.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import kitkat.auth.enumeration.CustomJwtClaims;
import org.springframework.stereotype.Component;

@Component
public class JwtClaimUtils {

    public String extractPermissionsClaim(String accessToken) {
        DecodedJWT decodedToken = JWT.decode(accessToken);
        return decodedToken.getClaim(CustomJwtClaims.AUTHORITIES.getValue()).asString();
    }

    public String extractSubjectClaim(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim(CustomJwtClaims.SUBJECT.getValue()).asString();
    }
}
