package br.com.bookstoreconsumer.adapters.configuration;

import com.auth0.jwt.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class JwtValidationFilter extends OncePerRequestFilter {

    private final SecurityPort securityPort;

    public JwtValidationFilter(SecurityPort securityPort) {
        this.securityPort = securityPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String headerAuthorization = request.getHeader("Authorization");
        if (headerAuthorization == null || !headerAuthorization.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            filterChain.doFilter(request, response);
            return;
        }

        String token = headerAuthorization.substring(7);
        boolean isValid = securityPort.validJwt(token);
        if (!isValid) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            filterChain.doFilter(request, response);
            return;
        }

        String email = JWT.decode(token).getSubject();
        String USER = "USER";
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(email, token,
                        Collections.singletonList(new SimpleGrantedAuthority(USER)));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
