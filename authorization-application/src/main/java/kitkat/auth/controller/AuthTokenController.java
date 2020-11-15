package kitkat.auth.controller;

import kitkat.auth.api.AuthTokenApi;
import kitkat.auth.model.dto.AuthTokenDto;
import kitkat.auth.model.dto.AuthenticationRequestDto;
import kitkat.auth.service.AuthTokenService;
import kitkat.auth.service.AuthenticationService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthTokenController implements AuthTokenApi {

    private final AuthenticationService authenticationService;
    private final AuthTokenService authTokenService;

    public AuthTokenController(AuthenticationService authenticationService,
                               AuthTokenService authTokenService) {
        this.authenticationService = authenticationService;
        this.authTokenService = authTokenService;
    }

    @Override
    public AuthTokenDto authenticate(HttpServletRequest httpServletRequest, AuthenticationRequestDto authenticationRequest) {
        return authenticationService.authenticate(httpServletRequest, authenticationRequest);
    }

    @Override
    public void invalidateRefreshToken(HttpServletRequest httpServletRequest) {
        authTokenService.invalidateRefreshToken(httpServletRequest);
    }

    @Override
    public AuthTokenDto updateAccessToken(HttpServletRequest httpServletRequest) {
        return authTokenService.updateAccessToken(httpServletRequest);
    }

    @Override
    public void invalidateRefreshTokensByUsername(HttpServletRequest httpServletRequest) {
        authTokenService.invalidateRefreshTokensByUsername(httpServletRequest);
    }
}
