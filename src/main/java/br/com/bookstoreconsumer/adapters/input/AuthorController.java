package br.com.bookstoreconsumer.adapters.input;

import br.com.bookstoreconsumer.adapters.clients.dto.AuthorAndBookResponse;
import br.com.bookstoreconsumer.adapters.input.dto.AuthorRequest;
import br.com.bookstoreconsumer.application.AuthorUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorUseCase authorsUseCase;

    public AuthorController(AuthorUseCase authorsUseCase) {
        this.authorsUseCase = authorsUseCase;
    }

    @GetMapping
    public ResponseEntity<List<AuthorAndBookResponse>> getAuthors(@RequestParam(value = "books", required = false, defaultValue = "false") boolean books) {
        return ResponseEntity.ok(authorsUseCase.getAuthors(books));
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<AuthorAndBookResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorsUseCase.getAuthorById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveAuthor(@RequestBody AuthorRequest authorRequest) {
        String id = authorsUseCase.saveAuthor(authorRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/author/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorAndBookResponse> updateAuthor(@PathVariable Long id,
                                                              @RequestBody AuthorRequest authorRequest) {
        AuthorAndBookResponse authorResponse = authorsUseCase.updateAuthor(id, authorRequest);
        return ResponseEntity.ok(authorResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorsUseCase.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
