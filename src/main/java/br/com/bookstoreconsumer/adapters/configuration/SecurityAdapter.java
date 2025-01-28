package br.com.bookstoreconsumer.adapters.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SecurityAdapter implements SecurityPort {

    private static final String SECRET_KEY = "chave-de-seguranca";

    @Override
    public Cookie generateJwtCookie(String email, String name) {
        String jwtToken = JWT.create()
                .withSubject(email)
                .withClaim("name", name)
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                .sign(Algorithm.HMAC256(SECRET_KEY));

        Cookie jwtCookie = new Cookie("JWT_TOKEN", jwtToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60 * 24);

        return jwtCookie;
    }

    @Override
    public boolean validJwtCookie(Cookie cookie) {
        try {
            String token = cookie.getValue();
            JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
