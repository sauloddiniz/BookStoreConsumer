package br.com.bookstoreconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients(basePackages = "br.com.bookstoreconsumer.adapters")
public class BookStoreConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreConsumerApplication.class, args);
    }
}
