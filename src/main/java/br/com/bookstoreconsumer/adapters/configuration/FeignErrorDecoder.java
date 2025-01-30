package br.com.bookstoreconsumer.adapters.configuration;

import br.com.bookstoreconsumer.core.exception.ClientApiFeignException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Configuration
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    @SneakyThrows
    public Exception decode(String methodName, Response response) {
        log.error("Decodificando erro para o método: {}", methodName);
        log.error("Status da resposta recebido: {}", response.status());

        byte[] bodyBytes = StreamUtils.copyToByteArray(response.body().asInputStream());

        final String error = new String(bodyBytes, StandardCharsets.UTF_8);
        log.error("Erro recebido no corpo da resposta: {}", error);

        FeignError objectError;
        try {
            objectError = converterStringToObject(error);
            objectError.setHttpStatus(response.status());
            log.debug("Erro convertido para objeto: {}", objectError);
        } catch (Exception e) {
            log.error("Falha ao converter erro para objeto FeignError: {}", error, e);
            throw e;
        }

        throw new ClientApiFeignException(objectError);
    }

    private FeignError converterStringToObject(final String error) throws JsonProcessingException {
        log.debug("Iniciando a conversão do erro para um objeto FeignError.");

        final ObjectMapper mapper = new ObjectMapper();
        FeignError feignError = mapper.readValue(error, FeignError.class);

        log.debug("Conversão concluída com sucesso: {}", feignError);
        return feignError;
    }
}
