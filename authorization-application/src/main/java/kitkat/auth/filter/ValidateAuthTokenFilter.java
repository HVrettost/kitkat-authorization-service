package kitkat.auth.filter;

import kitkat.auth.util.JwtUtils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Optional;

public class ValidateAuthTokenFilter implements Filter {

    private static final String ACCESS_TOKEN_URI = "/api/auth/token";

    private final JwtUtils jwtUtils;

    public ValidateAuthTokenFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (!isCreateAccessTokenRequest(httpRequest)) {
            String token = extractBearerToken(httpRequest);
            jwtUtils.validateToken(token);
        }

        chain.doFilter(request, response);
    }

    private boolean isCreateAccessTokenRequest(HttpServletRequest request) {
        return HttpMethod.POST.name().equals(request.getMethod()) && ACCESS_TOKEN_URI.equals(request.getRequestURI());
    }

    private String extractBearerToken(HttpServletRequest request) throws ServletException {
        Optional<String> authorizationHeaderValue = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION));
        return authorizationHeaderValue.orElseThrow(() -> new ServletException("")).replace("Bearer ", "");
    }
}
