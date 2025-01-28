package br.com.bookstoreconsumer.adapters.clients;

import br.com.bookstoreconsumer.adapters.clients.dto.AuthorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "bookstore-authors-api", url = "${api.bookstore.url}")
public interface AuthorsClientApi {

    @GetMapping("/authors")
    ResponseEntity<List<AuthorResponse>> getAuthors();
}
