package kitkat.auth.service;

import kitkat.auth.model.dto.AuthTokenDto;
import kitkat.auth.model.dto.AuthenticationRequestDto;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    AuthTokenDto authenticate(HttpServletRequest httpServletRequest, AuthenticationRequestDto authenticationRequest);
}
