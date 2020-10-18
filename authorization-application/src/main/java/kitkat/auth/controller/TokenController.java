package kitkat.auth.controller;

import kitkat.auth.api.TokenApi;
import kitkat.auth.model.AuthenticationRequestDto;
import kitkat.auth.model.AuthenticationResponseDto;
import kitkat.auth.service.AuthenticationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController implements TokenApi {

    private final AuthenticationService authenticationService;

    public TokenController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        return authenticationService.authenticate(authenticationRequestDto);
    }
}
