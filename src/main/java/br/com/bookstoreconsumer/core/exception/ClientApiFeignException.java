package br.com.bookstoreconsumer.core.exception;

import br.com.bookstoreconsumer.adapters.configuration.FeignError;

public class ClientApiFeignException extends RuntimeException {

    private static final ThreadLocal<FeignError> error = new ThreadLocal<>();

    public ClientApiFeignException(FeignError error) {
        ClientApiFeignException.error.set(error);
    }

    public FeignError getError() {
        return error.get();
    }

    public void clearErrorMessage() {
        error.remove();
    }

}
