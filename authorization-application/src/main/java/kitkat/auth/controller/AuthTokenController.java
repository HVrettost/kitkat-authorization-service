package kitkat.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RestController;

import kitkat.auth.api.AuthTokenApi;
import kitkat.auth.model.dto.AuthenticationRequestDto;
import kitkat.auth.service.AuthTokenService;
import kitkat.auth.service.AuthenticationService;

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
    public void authenticate(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             AuthenticationRequestDto authenticationRequest) {
        authenticationService.authenticate(httpServletRequest, httpServletResponse, authenticationRequest);
        httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    public void invalidateRefreshToken(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse) {
        authTokenService.invalidateRefreshToken(httpServletRequest);
        httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    public void updateAccessToken(HttpServletRequest httpServletRequest,
                                  HttpServletResponse httpServletResponse) {
        authTokenService.updateAccessToken(httpServletRequest, httpServletResponse);
        httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    public void invalidateRefreshTokensByUsername(HttpServletRequest httpServletRequest,
                                                  HttpServletResponse httpServletResponse) {
        authTokenService.invalidateRefreshTokensByUsername(httpServletRequest);
        httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
