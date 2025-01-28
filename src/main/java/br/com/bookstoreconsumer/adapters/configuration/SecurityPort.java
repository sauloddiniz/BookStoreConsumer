package br.com.bookstoreconsumer.adapters.configuration;

public interface SecurityPort {
    String generateJwt(String email);
    boolean validJwtCookie(String token);
}
