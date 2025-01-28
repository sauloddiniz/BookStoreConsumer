package br.com.bookstoreconsumer.configuration;

import br.com.bookstoreconsumer.core.domain.ClientApiFeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerMainConfig {

    @ExceptionHandler(ClientApiFeignException.class)
    public ResponseEntity<Object> clientApiFeignException(ClientApiFeignException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("date", ClientApiFeignException.getFeignError().getDate());
        body.put("error", ClientApiFeignException.getFeignError().getError());
        body.put("path", ClientApiFeignException.getFeignError().getPath());
        body.put("method", ClientApiFeignException.getFeignError().getMethod());
        return new ResponseEntity<>(body, HttpStatus.valueOf(ClientApiFeignException.getFeignError().getHttpStatus()));
    }

    private static Map<String, Object> extractErrorInfo(Exception ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("date", LocalDateTime.now());
        body.put("error", ex.getMessage());
        body.put("path", request.getRequestURI());
        body.put("method", request.getMethod());
        return body;
    }

}

