package kitkat.auth.jwt.validator;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.exception.error.AuthError;
import kitkat.auth.jwt.factory.JwtVerifierFactory;
import kitkat.auth.validator.Validator;

@Component
public class JwtValidator implements Validator<String> {

    private final JwtVerifierFactory jwtVerifierFactory;

    public JwtValidator(JwtVerifierFactory jwtVerifierFactory) {
        this.jwtVerifierFactory = jwtVerifierFactory;
    }

    @Override
    public void validate(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            jwtVerifierFactory.getJwtVerifier(decodedJWT);
        } catch (TokenExpiredException tokenExpiredException) {
            throw new AuthorizationException(AuthError.TOKEN_EXPIRED);
        } catch (JWTVerificationException jwtVerificationException) {
            throw new AuthorizationException(AuthError.INVALID_TOKEN);
        }
    }
}
