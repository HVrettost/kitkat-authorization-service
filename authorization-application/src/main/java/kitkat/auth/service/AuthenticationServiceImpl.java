package kitkat.auth.service;

import kitkat.auth.model.dto.AuthTokenDto;
import kitkat.auth.model.dto.AuthenticationRequestDto;

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
    private final AuthTokenService authTokenService;
    private final AuthRoleService authRoleService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     AuthTokenService authTokenService,
                                     AuthRoleService authRoleService) {
        this.authenticationManager = authenticationManager;
        this.authTokenService = authTokenService;
        this.authRoleService = authRoleService;
    }

    @Override
    @Transactional
    public AuthTokenDto authenticate(AuthenticationRequestDto authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

            String permissions = getUserPermissions(authenticationRequest.getUsername());
            AuthTokenDto authTokenDto = authTokenService.createAuthTokenDto(authenticationRequest.getUsername(), permissions);

            return authTokenService.invalidateTokenIfExistsAndSaveNewToken(authTokenDto);
        } catch(BadCredentialsException badCredentialsException) {
            LOGGER.info("Bad credentials given for user: ",  badCredentialsException);
            throw new BadCredentialsException("Bad credentials given!");
        }
    }

    private String getUserPermissions(String username) {
        String authRole = authRoleService.getAuthRoleByUsername(username).getRole();
        return authRoleService.getPermissionsByAuthRole(authRole).getPermissions();
    }
}
