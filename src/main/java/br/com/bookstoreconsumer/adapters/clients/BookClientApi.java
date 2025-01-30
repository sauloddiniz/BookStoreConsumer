package br.com.bookstoreconsumer.adapters.clients;

import br.com.bookstoreconsumer.adapters.clients.dto.AuthorAndBookResponse;
import br.com.bookstoreconsumer.adapters.clients.dto.BookRequest;
import br.com.bookstoreconsumer.adapters.clients.dto.BookResponse;
import br.com.bookstoreconsumer.adapters.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "bookstore-books-api", url = "${api.bookstore.url}",
        configuration = FeignConfiguration.class)
public interface BookClientApi {

    @GetMapping("/authors/{authorId}/books")
    ResponseEntity<AuthorAndBookResponse> getBooks(@PathVariable("authorId") Long authorId);

    @GetMapping("/authors/{authorId}/books/{id}")
    ResponseEntity<AuthorAndBookResponse> getBookById(@PathVariable("authorId") Long authorId,
                                                       @PathVariable("id") Long id);

    @PostMapping("/authors/{authorId}/books")
    ResponseEntity<Void> saveBook(@PathVariable("authorId") Long authorId,
                                  @RequestBody BookRequest bookRequest);

    @PutMapping("/authors/{authorId}/books/{id}")
    ResponseEntity<BookResponse> updateBook(@PathVariable("authorId") Long authorId,
                                            @PathVariable("id") Long id,
                                            @RequestBody BookRequest bookRequest);

    @DeleteMapping("/authors/{authorId}/books/{id}")
    ResponseEntity<Void> deleteBook(@PathVariable("authorId") Long authorId,
                                    @PathVariable("id") Long id);
}
