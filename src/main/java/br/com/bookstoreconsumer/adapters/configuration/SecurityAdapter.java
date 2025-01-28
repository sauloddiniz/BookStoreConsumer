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
    public  String generateJwt(String email) {
        return JWT.create()
                .withSubject(email)
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    @Override
    public boolean validJwt(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
