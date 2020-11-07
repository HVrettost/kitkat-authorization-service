package kitkat.auth.service;

import kitkat.auth.enumeration.TokenType;
import kitkat.auth.model.dto.AuthTokenDto;
import kitkat.auth.model.dto.AuthenticationRequestDto;
import kitkat.auth.util.JwtUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AuthTokenService authTokenService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     JwtUtils jwtUtils,
                                     AuthTokenService authTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.authTokenService = authTokenService;
    }

    @Override
    @Transactional
    public AuthTokenDto authenticate(AuthenticationRequestDto authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

            AuthTokenDto authTokenDto = createAuthTokenDto(authenticationRequest);

            return authTokenService.invalidateTokenIfExistsAndSaveNewToken(authTokenDto);
        } catch(BadCredentialsException badCredentialsException) {
            LOGGER.info("Bad credentials given for user: ",  badCredentialsException);
            throw new BadCredentialsException("Bad credentials given!");
        }
    }

    private AuthTokenDto createAuthTokenDto(AuthenticationRequestDto authenticationRequest) {
        AuthTokenDto authTokenDto = new AuthTokenDto();
        authTokenDto.setTokenType(TokenType.BEARER.getValue());
        authTokenDto.setUsername(authenticationRequest.getUsername());
        authTokenDto.setAccessToken(jwtUtils.generateAccessToken(authenticationRequest.getUsername()));
        authTokenDto.setRefreshToken(jwtUtils.generateRefreshToken(authenticationRequest.getUsername()));

        return authTokenDto;
    }
}
