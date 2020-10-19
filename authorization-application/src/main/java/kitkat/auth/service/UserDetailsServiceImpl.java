package kitkat.auth.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //make implementation to call the user-service microservice
        return new User("username", new BCryptPasswordEncoder().encode("password"), getGrantedAuthorities(""));
    }

    private List<GrantedAuthority> getGrantedAuthorities(String userRoles) {
        return userRoles != null && !userRoles.isEmpty() ? Arrays.stream(userRoles.split(", ")).
                map(SimpleGrantedAuthority::new).
                collect(Collectors.toList()) : Collections.emptyList();
    }
}
