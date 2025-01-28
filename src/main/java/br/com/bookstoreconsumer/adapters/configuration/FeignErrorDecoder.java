package br.com.bookstoreconsumer.adapters.configuration;

import br.com.bookstoreconsumer.core.exception.ClientApiFeignException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodName, Response response) {
        byte[] bodyBytes = null;
        try {
            bodyBytes = StreamUtils.copyToByteArray(response.body().asInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final String error = new String(bodyBytes, StandardCharsets.UTF_8);
        FeignError objectError = null;
        try {
            objectError = converterStringToObject(error);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        objectError.setHttpStatus(response.status());
        throw new ClientApiFeignException(objectError);
    }


    private FeignError converterStringToObject(final String error) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(error, FeignError.class);
    }

}
