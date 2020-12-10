package kitkat.auth.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.databind.ObjectMapper;

import kitkat.auth.exception.AuthorizationException;
import kitkat.auth.jwt.util.JwtClaimUtils;
import kitkat.auth.jwt.validator.JwtValidator;
import kitkat.auth.util.HeaderUtils;

public class AuthorizationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);

    private static final String ACCESS_TOKEN_ENDPOINT = "/api/auth/token";
    private static final String REFRESH_TOKEN_ENDPOINT = "/api/auth/token/refresh";

    private final JwtValidator jwtValidator;
    private final JwtClaimUtils jwtClaimUtils;
    private final HeaderUtils headerUtils;
    private final ObjectMapper objectMapper;

    public AuthorizationFilter(JwtValidator jwtValidator,
                               JwtClaimUtils jwtClaimUtils,
                               ObjectMapper objectMapper,
                               HeaderUtils headerUtils) {
        this.jwtValidator = jwtValidator;
        this.jwtClaimUtils = jwtClaimUtils;
        this.objectMapper = objectMapper;
        this.headerUtils = headerUtils;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (isAuthenticateRequest(httpRequest) || isOptionsHttpMethod(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            if (isUpdateAccessTokenRequest(httpRequest)) {
                String refreshToken = headerUtils.extractRefreshToken(httpRequest);
                jwtValidator.validate(refreshToken);
                chain.doFilter(request, response);
                return;
            }

            String accessToken = headerUtils.extractAccessToken(httpRequest);
            jwtValidator.validate(accessToken);
            String permissions = jwtClaimUtils.extractPermissionsClaim(accessToken);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, null, getUserGrantedAuthorities(permissions)));
        } catch (AuthorizationException authorizationException) {
            LOGGER.error(authorizationException.getErrorDetails().getMessage(), authorizationException);
            setErrorDetailsAndStatusInServletResponse(httpResponse, authorizationException);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isUpdateAccessTokenRequest(HttpServletRequest request) {
        return HttpMethod.PUT.name().equals(request.getMethod()) && REFRESH_TOKEN_ENDPOINT.equals(request.getRequestURI());
    }

    private boolean isAuthenticateRequest(HttpServletRequest request) {
        return HttpMethod.POST.name().equals(request.getMethod()) && ACCESS_TOKEN_ENDPOINT.equals(request.getRequestURI());
    }

    private boolean isOptionsHttpMethod(HttpServletRequest request) {
        return HttpMethod.OPTIONS.name().equals(request.getMethod());
    }

    private List<GrantedAuthority> getUserGrantedAuthorities(String authorities) {
        return authorities == null
                ? Collections.emptyList()
                : List.of(authorities.split(" "))
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
    }

    private void setErrorDetailsAndStatusInServletResponse(HttpServletResponse httpResponse,
                                                           AuthorizationException authorizationException) throws IOException {
        httpResponse.setStatus(authorizationException.getHttpStatus().value());
        httpResponse.getOutputStream().write(objectMapper.writeValueAsBytes(authorizationException.getErrorDetails()));
    }
}
