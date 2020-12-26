package kitkat.auth.service;

import kitkat.auth.validator.UsernameValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import kitkat.auth.model.dto.AuthenticationRequestDto;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UsernameValidator usernameValidator;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     UsernameValidator usernameValidator) {
        this.authenticationManager = authenticationManager;
        this.usernameValidator = usernameValidator;
    }

    @Override
    public void authenticate(AuthenticationRequestDto authenticationRequest) {
        usernameValidator.validate(authenticationRequest.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));
    }
}
