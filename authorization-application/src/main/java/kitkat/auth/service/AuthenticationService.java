package kitkat.auth.service;

import kitkat.auth.model.AuthenticationRequestDto;
import kitkat.auth.model.AuthenticationResponseDto;

public interface AuthenticationService {

    AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequest);
}
