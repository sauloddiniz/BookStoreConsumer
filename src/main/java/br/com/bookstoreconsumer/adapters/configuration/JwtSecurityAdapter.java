package br.com.bookstoreconsumer.adapters.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class JwtSecurityAdapter implements JwtSecurityPort {

    private static final String SECRET_KEY = "chave-de-seguranca";

    @Override
    public  String generateJwt(OAuth2User oAuth2User) {

        return JWT.create()
                .withSubject(oAuth2User.getAttribute("sub"))
                .withClaim("email", Objects.requireNonNull(oAuth2User.getAttribute("email")).toString())
                .withClaim("name", Objects.requireNonNull(oAuth2User.getAttribute("name")).toString())
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
