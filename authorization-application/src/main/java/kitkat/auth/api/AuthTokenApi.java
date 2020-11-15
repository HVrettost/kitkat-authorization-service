package kitkat.auth.api;

import kitkat.auth.model.dto.AuthTokenDto;
import kitkat.auth.model.dto.AuthenticationRequestDto;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/api/auth/token")
public interface AuthTokenApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    AuthTokenDto authenticate(HttpServletRequest httpServletRequest, @RequestBody AuthenticationRequestDto authenticationRequest);

    @PreAuthorize("hasAuthority('REFRESH_TOKEN_DELETE')")
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    void invalidateRefreshToken(HttpServletRequest httpServletRequest);

    @PreAuthorize("hasAuthority('REFRESH_TOKEN_ALL_DELETE')")
    @DeleteMapping(value = "/all",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    void invalidateRefreshTokensByUsername(HttpServletRequest httpServletRequest);

    @PutMapping(value = "/refresh",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    AuthTokenDto updateAccessToken(HttpServletRequest request);
}
