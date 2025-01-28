package br.com.bookstoreconsumer.adapters.configuration;

import jakarta.servlet.http.Cookie;

public interface SecurityPort {
    Cookie generateJwtCookie(String email, String name);
    boolean validJwtCookie(Cookie cookie);
}
