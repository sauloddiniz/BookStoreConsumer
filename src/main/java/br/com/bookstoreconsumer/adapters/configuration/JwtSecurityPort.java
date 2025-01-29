package br.com.bookstoreconsumer.adapters.configuration;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface JwtSecurityPort {
    String generateJwt(OAuth2User oAuth2User);
    boolean validJwt(String token);
}
