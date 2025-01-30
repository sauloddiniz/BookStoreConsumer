package br.com.bookstoreconsumer.adapters.input;

import br.com.bookstoreconsumer.adapters.clients.dto.AuthorAndBookResponse;
import br.com.bookstoreconsumer.adapters.clients.dto.BookRequest;
import br.com.bookstoreconsumer.adapters.clients.dto.BookResponse;
import br.com.bookstoreconsumer.application.BookUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/authors/{authorId}/books")
public class BookController {

    private final BookUseCase bookUseCase;

    public BookController(BookUseCase bookUseCase) {
        this.bookUseCase = bookUseCase;
    }

    @GetMapping()
    public ResponseEntity<AuthorAndBookResponse> getBooks(@PathVariable Long authorId) {
        return ResponseEntity.ok(bookUseCase.getListBooks(authorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorAndBookResponse> getBookById(@PathVariable Long authorId, @PathVariable Long id) {
        return ResponseEntity.ok(bookUseCase.getBookById(authorId, id));
    }

    @PostMapping()
    public ResponseEntity<Void> saveBook(@PathVariable Long authorId, @RequestBody BookRequest book) {
        String id = bookUseCase.saveBook(authorId, book);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/authors/{authorId}/books/{id}")
                .buildAndExpand(authorId.toString(), id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long authorId,
                                           @PathVariable Long id,
                                           @RequestBody BookRequest bookRequest) {
        BookResponse authorResponse = bookUseCase.updateBook(authorId, id, bookRequest);
        return ResponseEntity.ok(authorResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long authorId,
                                           @PathVariable Long id) {
        bookUseCase.deleteBook(authorId, id);
        return ResponseEntity.noContent().build();
    }
}
