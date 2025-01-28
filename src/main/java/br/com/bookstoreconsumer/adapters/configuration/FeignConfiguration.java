package br.com.bookstoreconsumer.adapters.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public FeignErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public Logger.Level feignLogger(){
        return Logger.Level.FULL;
    }

}
