package kitkat.auth.service;

import kitkat.auth.model.dto.AuthenticationRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    void authenticate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationRequestDto authenticationRequest);
}
