package br.com.bookstoreconsumer.core.exception;

public class JwtAuthenticationException extends RuntimeException {

    private static final String MESSAGE = "Missing or invalid JWT token:";

    public JwtAuthenticationException() {
        super(MESSAGE);
    }
}
