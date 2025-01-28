package br.com.bookstoreconsumer.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignLoggerConfig {

    @Bean
    public Logger.Level feignLogger(){
        return Logger.Level.FULL;
    }
}
