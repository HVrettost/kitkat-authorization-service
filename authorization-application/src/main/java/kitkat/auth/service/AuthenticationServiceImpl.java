package kitkat.auth.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitkat.auth.util.CookieUtils;
import kitkat.auth.util.HeaderUtils;
import kitkat.auth.util.JwtUtils;
import kitkat.auth.model.dto.AuthenticationRequestDto;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final AuthTokenService authTokenService;
    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     AuthTokenService authTokenService,
                                     JwtUtils jwtUtils,
                                     CookieUtils cookieUtils) {
        this.authenticationManager = authenticationManager;
        this.authTokenService = authTokenService;
        this.jwtUtils = jwtUtils;
        this.cookieUtils = cookieUtils;
    }

    @Override
    @Transactional
    public void authenticate(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             AuthenticationRequestDto authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));

        String userAgent = HeaderUtils.extractUserAgent(httpServletRequest);
        String permissions = authTokenService.getUserPermissions(authenticationRequest.getUsername());
        String accessToken = jwtUtils.generateAccessToken(authenticationRequest.getUsername(), permissions);
        String refreshToken = jwtUtils.generateRefreshToken(authenticationRequest.getUsername());

        authTokenService.invalidateRefreshTokenIfExistsAndSaveNew(refreshToken, authenticationRequest.getUsername(), userAgent);

        httpServletResponse.addCookie(cookieUtils.createAccessTokenCookie(accessToken));
        httpServletResponse.addCookie(cookieUtils.createRefreshTokenCookie(refreshToken));
    }
}
