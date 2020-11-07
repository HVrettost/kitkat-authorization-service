package kitkat.auth.service;

import kitkat.auth.gateway.UserServiceGateway;
import kitkat.auth.model.dto.UserDto;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceGateway userServiceGateway;

    public UserDetailsServiceImpl(UserServiceGateway userServiceGateway) {
        this.userServiceGateway = userServiceGateway;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = Optional
                .ofNullable(userServiceGateway.getUserByUsername(username))
                .map(HttpEntity::getBody)
                .orElse(new UserDto());
        return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(""));
    }

    private List<GrantedAuthority> getGrantedAuthorities(String userRoles) {
        return userRoles != null && !userRoles.isEmpty() ? Arrays.stream(userRoles.split(", ")).
                map(SimpleGrantedAuthority::new).
                collect(Collectors.toList()) : Collections.emptyList();
    }
}
