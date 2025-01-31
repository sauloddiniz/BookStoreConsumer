package br.com.bookstoreconsumer.adapters.configuration;

import feign.Logger;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
public class FeignConfiguration {

    @Bean
    public FeignErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new CustomFeignRetryer(3, TimeUnit.SECONDS.toMillis(1));
    }

    @Bean
    public Logger.Level feignLogger(){
        return Logger.Level.FULL;
    }

}
