package br.com.bookstoreconsumer.adapters.filters;

import br.com.bookstoreconsumer.adapters.configuration.SecurityPort;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    private static final String JWT_TOKEN = "JWT_TOKEN";
    public static final String USER = "USER";
    private final SecurityPort securityPort;

    public JwtValidationFilter(SecurityPort securityPort) {
        this.securityPort = securityPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional<Cookie> jwtCookie = getCookie(request);

            if (jwtCookie.isPresent()) {
                Cookie cookie = jwtCookie.get();

                boolean isValid = securityPort.validJwtCookie(cookie);

                if (!isValid) throw new JWTVerificationException("");

                String email = JWT.decode(cookie.getValue()).getSubject();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, cookie.getValue(),
                                Collections.singletonList(new SimpleGrantedAuthority(USER)));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.clearContext();
                throw new JWTVerificationException("");
            }
        } catch (JWTVerificationException e) {
            SecurityContextHolder.clearContext();
            throw new JWTVerificationException("");
        }

        filterChain.doFilter(request, response);
    }

    private Optional<Cookie> getCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return Optional.empty();
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(JWT_TOKEN)) {
                return Optional.of(cookie);
            }
        }
        return Optional.empty();
    }
}
