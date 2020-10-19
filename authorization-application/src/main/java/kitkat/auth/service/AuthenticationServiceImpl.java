package kitkat.auth.service;

import kitkat.auth.model.AuthenticationRequestDto;
import kitkat.auth.model.AuthenticationResponseDto;
import kitkat.auth.util.JwtUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

            return new AuthenticationResponseDto(jwtUtils.generateAccessToken(authenticationRequest));
        } catch(BadCredentialsException badCredentialsException) {
            LOGGER.info("Bad credentials given");
            throw new BadCredentialsException("Bad credentials given!");
        }
    }
}
