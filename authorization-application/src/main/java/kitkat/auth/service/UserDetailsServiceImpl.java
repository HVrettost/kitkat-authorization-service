package kitkat.auth.service;

import kitkat.auth.gateway.UserServiceGateway;
import kitkat.auth.model.dto.UserCredentialsDto;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceGateway userServiceGateway;

    public UserDetailsServiceImpl(UserServiceGateway userServiceGateway) {
        this.userServiceGateway = userServiceGateway;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentialsDto credentials = userServiceGateway.getUserCredentialsByUsername(username);

        try {
            return new User(credentials.getUsername(), credentials.getPassword(), Collections.emptyList());
        } catch (IllegalArgumentException ex) {
            throw new BadCredentialsException(String.format("No user was found for username %s", username), ex);
        }
    }
}
