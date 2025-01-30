package br.com.bookstoreconsumer.adapters.clients;

import br.com.bookstoreconsumer.adapters.clients.dto.AuthorAndBookResponse;
import br.com.bookstoreconsumer.adapters.configuration.FeignConfiguration;
import br.com.bookstoreconsumer.adapters.input.dto.AuthorRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bookstore-authors-api", url = "${api.bookstore.url}/authors", configuration = FeignConfiguration.class)
public interface AuthorsClientApi {

    @GetMapping()
    ResponseEntity<List<AuthorAndBookResponse>> getAuthors(@RequestParam(value = "books", required = false, defaultValue = "false") boolean books);

    @GetMapping("/{id}")
    ResponseEntity<AuthorAndBookResponse> getAuthorById(@PathVariable Long id);

    @PostMapping()
    ResponseEntity<Void> saveAuthor(@RequestBody AuthorRequest authorRequest);

    @PutMapping("/{id}")
    ResponseEntity<AuthorAndBookResponse> updateAuthor(@PathVariable Long id,
                                                       @RequestBody AuthorRequest authorRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAuthor(@PathVariable Long id);
}
