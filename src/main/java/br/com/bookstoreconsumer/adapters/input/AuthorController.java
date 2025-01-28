package br.com.bookstoreconsumer.adapters.input;

import br.com.bookstoreconsumer.adapters.input.dto.AuthorRequest;
import br.com.bookstoreconsumer.application.AuthorUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorUseCase authorsUseCase;

    public AuthorController(AuthorUseCase authorsUseCase) {
        this.authorsUseCase = authorsUseCase;
    }

    @GetMapping
    public ResponseEntity<List<AuthorRequest>> getAuthors() {
        return ResponseEntity.ok(authorsUseCase.getAuthors());
    }
}
