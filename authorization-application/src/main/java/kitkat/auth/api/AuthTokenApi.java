package kitkat.auth.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import kitkat.auth.exception.CoreException;
import kitkat.auth.model.dto.AuthenticationRequestDto;

@RequestMapping(value = "/api/auth/token")
public interface AuthTokenApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    void authenticate(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse,
                      @RequestBody AuthenticationRequestDto authenticationRequest) throws CoreException;

    @PreAuthorize("hasAuthority('REFRESH_TOKEN_DELETE')")
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    void invalidateRefreshToken(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse) throws CoreException;

    @PreAuthorize("hasAuthority('REFRESH_TOKEN_ALL_DELETE')")
    @DeleteMapping(value = "/all",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    void invalidateRefreshTokensByUsername(HttpServletRequest httpServletRequest,
                                           HttpServletResponse httpServletResponse) throws CoreException;

    @PutMapping(value = "/refresh",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    void updateAccessToken(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse) throws CoreException;
}
