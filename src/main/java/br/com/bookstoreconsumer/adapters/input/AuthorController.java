package br.com.bookstoreconsumer.adapters.input;

import br.com.bookstoreconsumer.adapters.clients.dto.AuthorResponse;
import br.com.bookstoreconsumer.adapters.input.dto.AuthorRequest;
import br.com.bookstoreconsumer.application.AuthorUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<AuthorResponse>> getAuthors(@RequestParam(value = "books", required = false, defaultValue = "false") boolean books) {
        return ResponseEntity.ok(authorsUseCase.getAuthors(books));
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorsUseCase.getAuthorById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveAuthor(@RequestBody AuthorRequest authorRequest) {
        String location = authorsUseCase.saveAuthor(authorRequest);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable Long id,
                                                       @RequestBody AuthorRequest authorRequest) {
        AuthorResponse authorResponse = authorsUseCase.updateAuthor(id, authorRequest);
        return ResponseEntity.ok(authorResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> updateAuthor(@PathVariable Long id) {
        authorsUseCase.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
