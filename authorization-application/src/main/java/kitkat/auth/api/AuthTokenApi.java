package kitkat.auth.api;

import kitkat.auth.model.dto.AuthTokenDto;
import kitkat.auth.model.dto.AuthenticationRequestDto;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RequestMapping(value = "/api/auth/token")
public interface AuthTokenApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    AuthTokenDto authenticate(@RequestBody AuthenticationRequestDto authenticationRequestDto);

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    void invalidateToken(@RequestBody AuthTokenDto authTokenDto);

    @PutMapping(value = "/refresh",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    AuthTokenDto updateAccessToken(@RequestBody AuthTokenDto authTokenDto);
}
