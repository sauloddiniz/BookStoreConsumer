package br.com.bookstoreconsumer.adapters.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class JwtUtil {

    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String SUB = "sub";
    @Value("${jwt.secret}")
    private String secretKey;

    public String generateJwt(OAuth2User oAuth2User) {
        return JWT.create()
                .withSubject(oAuth2User.getAttribute(SUB))
                .withClaim(EMAIL, Objects.requireNonNull(oAuth2User.getAttribute(EMAIL)).toString())
                .withClaim(NAME, Objects.requireNonNull(oAuth2User.getAttribute(NAME)).toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public boolean validJwt(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmail(String token) {
        return JWT.decode(token).getClaim(EMAIL).asString();
    }

}
