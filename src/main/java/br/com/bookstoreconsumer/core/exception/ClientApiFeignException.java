package br.com.bookstoreconsumer.core.exception;

import br.com.bookstoreconsumer.adapters.configuration.FeignError;


public class ClientApiFeignException extends RuntimeException {

    private static FeignError feignError;

    public ClientApiFeignException(FeignError error) {
       feignError = error;
    }

    public static FeignError getFeignError() {
        return feignError;
    }
}
