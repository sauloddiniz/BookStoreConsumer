package br.com.bookstoreconsumer.configuration;

import br.com.bookstoreconsumer.core.exception.ClientApiFeignException;
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
    public ResponseEntity<Object> clientApiFeignException(ClientApiFeignException ex, HttpServletRequest request) {
        Map<String, Object> body = extractErrorInfo(ex, request);
        Map<String, Object> externalError = new HashMap<>();
        externalError.put("date", ex.getError().getDate());
        externalError.put("error", ex.getError().getError());
        externalError.put("path", ex.getError().getPath());
        externalError.put("method", ex.getError().getMethod());
        body.put("externalError", externalError);
        int status = ex.getError().getHttpStatus();
        ex.clearErrorMessage();
        return new ResponseEntity<>(body, HttpStatus.valueOf(status));
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

