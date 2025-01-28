package br.com.bookstoreconsumer.adapters.clients;

import br.com.bookstoreconsumer.adapters.clients.dto.AuthorResponse;
import br.com.bookstoreconsumer.adapters.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "bookstore-authors-api", url = "${api.bookstore.url}", configuration = FeignConfiguration.class)
public interface AuthorsClientApi {

    @GetMapping("/authors")
    ResponseEntity<List<AuthorResponse>> getAuthors();

    @GetMapping("/authors/{id}")
    ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id);
}
