package br.com.bookstoreconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableFeignClients
@EnableWebSecurity
public class BookStoreConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreConsumerApplication.class, args);
    }

}
