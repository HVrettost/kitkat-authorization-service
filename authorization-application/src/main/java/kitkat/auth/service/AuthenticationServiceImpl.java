package kitkat.auth.service;

import kitkat.auth.model.dto.AuthTokenDto;
import kitkat.auth.model.dto.AuthenticationRequestDto;

import kitkat.auth.util.HeaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final AuthTokenService authTokenService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     AuthTokenService authTokenService) {
        this.authenticationManager = authenticationManager;
        this.authTokenService = authTokenService;
    }

    @Override
    @Transactional
    public AuthTokenDto authenticate(HttpServletRequest httpServletRequest, AuthenticationRequestDto authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));

            String permissions = authTokenService.getUserPermissions(authenticationRequest.getUsername());
            String userAgent = HeaderUtils.extractUserAgent(httpServletRequest);
            AuthTokenDto authTokenDto = authTokenService.createAuthTokenDto(authenticationRequest.getUsername(), permissions, userAgent);

            authTokenService.invalidateRefreshTokenIfExistsAndSaveNew(authTokenDto.getRefreshToken(), authTokenDto.getUsername(), userAgent);

            return authTokenDto;
        } catch (BadCredentialsException badCredentialsException) {
            LOGGER.info("Bad credentials given for user: ",  badCredentialsException);
            throw new BadCredentialsException("Bad credentials given!");
        }
    }

}
