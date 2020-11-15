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

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import kitkat.auth.util.HeaderUtils;
import kitkat.auth.util.JwtUtils;

public class ValidateTokenFilter implements Filter {

    private static final String ACCESS_TOKEN_ENDPOINT = "/api/auth/token";
    private static final String REFRESH_TOKEN_ENDPOINT = "/api/auth/token/refresh";

    private final JwtUtils jwtUtils;

    public ValidateTokenFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (!isAuthenticateRequest(httpRequest)) {
            String token = HeaderUtils.extractAuthorizationHeader(httpRequest);
            jwtUtils.validateToken(token);

            if (!isUpdateAccessTokenRequest(httpRequest)) {
                String permissions = jwtUtils.extractPermissionsClaim(token);
                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(null, null, getUserGrantedAuthorities(permissions)));
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

    private List<GrantedAuthority> getUserGrantedAuthorities(String authorities) {
        return authorities == null
                ? Collections.emptyList()
                : List.of(authorities.split(" "))
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
    }
}
