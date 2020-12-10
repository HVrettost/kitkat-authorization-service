package kitkat.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> generateAccessToken(HttpServletRequest httpServletRequest,
                                                    AuthenticationRequestDto authenticationRequest) {
        authenticationService.authenticate(authenticationRequest);
        HttpHeaders httpHeaders = authTokenService.createCookieHeadersForAuthorization(httpServletRequest, authenticationRequest.getUsername());

        return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> invalidateRefreshToken(HttpServletRequest httpServletRequest) {
        authTokenService.invalidateRefreshToken(httpServletRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> updateAccessToken(HttpServletRequest httpServletRequest) {
        HttpHeaders httpHeaders = authTokenService.updateCookieHeaderForAccessToken(httpServletRequest);
        return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> invalidateRefreshTokensByUsername(HttpServletRequest httpServletRequest) {
        authTokenService.invalidateRefreshTokensByUsername(httpServletRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
