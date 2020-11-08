package kitkat.auth.controller;

import kitkat.auth.api.AuthTokenApi;
import kitkat.auth.model.dto.AuthTokenDto;
import kitkat.auth.model.dto.AuthenticationRequestDto;
import kitkat.auth.service.AuthTokenService;
import kitkat.auth.service.AuthenticationService;
import org.springframework.web.bind.annotation.RestController;

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
    public AuthTokenDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        return authenticationService.authenticate(authenticationRequestDto);
    }

    @Override
    public void invalidateToken(AuthTokenDto authTokenDto) {
        authTokenService.invalidateToken(authTokenDto);
    }

    @Override
    public AuthTokenDto updateAccessToken(AuthTokenDto authTokenDto) {
        return authTokenService.updateAccessToken(authTokenDto);
    }
}
