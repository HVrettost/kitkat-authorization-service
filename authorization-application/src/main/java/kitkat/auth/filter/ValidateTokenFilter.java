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

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kitkat.auth.enumeration.ErrorDetails;
import kitkat.auth.exception.error.AuthError;
import kitkat.auth.util.HeaderUtils;
import kitkat.auth.util.JwtUtils;

public class ValidateTokenFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateTokenFilter.class);

    private static final String ACCESS_TOKEN_ENDPOINT = "/api/auth/token";
    private static final String REFRESH_TOKEN_ENDPOINT = "/api/auth/token/refresh";

    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    public ValidateTokenFilter(JwtUtils jwtUtils,
                               ObjectMapper objectMapper) {
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!isAuthenticateRequest(httpRequest) && !isOptionsHttpMethod(httpRequest)) {
            try {
                String token = HeaderUtils.extractAuthorizationHeader(httpRequest);
                jwtUtils.validateToken(token);

                if (!isUpdateAccessTokenRequest(httpRequest)) {
                    String permissions = jwtUtils.extractPermissionsClaim(token);
                    SecurityContextHolder.getContext()
                            .setAuthentication(new UsernamePasswordAuthenticationToken(null, null, getUserGrantedAuthorities(permissions)));
                }
            } catch (TokenExpiredException tokenExpiredException) {
                LOGGER.error(tokenExpiredException.getMessage(), tokenExpiredException);
                setErrorDetailsAndStatusInServletResponse(httpResponse, AuthError.TOKEN_EXPIRED);
                return;
            } catch (JWTVerificationException jwtVerificationException) {
                LOGGER.error(jwtVerificationException.getMessage(), jwtVerificationException);
                setErrorDetailsAndStatusInServletResponse(httpResponse, AuthError.INVALID_TOKEN);
                return;
            }
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

    private void setErrorDetailsAndStatusInServletResponse(HttpServletResponse httpResponse, AuthError authError) throws IOException {
        ErrorDetails errorDetails = new ErrorDetails(authError.getErrorCode(), authError.getMessage());
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getOutputStream().write(objectMapper.writeValueAsBytes(errorDetails));
    }
}
