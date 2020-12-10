package kitkat.auth.service;

import kitkat.auth.model.dto.AuthenticationRequestDto;

public interface AuthenticationService {

    void authenticate(AuthenticationRequestDto authenticationRequest);
}
