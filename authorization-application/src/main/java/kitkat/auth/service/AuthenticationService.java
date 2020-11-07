package kitkat.auth.service;

import kitkat.auth.model.dto.AuthTokenDto;
import kitkat.auth.model.dto.AuthenticationRequestDto;

public interface AuthenticationService {

    AuthTokenDto authenticate(AuthenticationRequestDto authenticationRequest);
}
