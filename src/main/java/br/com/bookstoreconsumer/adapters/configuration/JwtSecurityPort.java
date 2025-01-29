package br.com.bookstoreconsumer.adapters.configuration;

public interface JwtSecurityPort {
    String generateJwt(String email);
    boolean validJwtCookie(String token);
}
