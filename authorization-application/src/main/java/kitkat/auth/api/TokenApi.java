package kitkat.auth.api;

import kitkat.auth.model.AuthenticationRequestDto;
import kitkat.auth.model.AuthenticationResponseDto;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/auth")
public interface TokenApi {

    @PostMapping(value = "/token",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    AuthenticationResponseDto authenticate(@RequestBody AuthenticationRequestDto authenticationRequestDto);
}
