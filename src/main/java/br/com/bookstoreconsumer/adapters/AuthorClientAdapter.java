package br.com.bookstoreconsumer.adapters;

import br.com.bookstoreconsumer.adapters.clients.AuthorsClientApi;
import br.com.bookstoreconsumer.adapters.clients.dto.AuthorAndBookResponse;
import br.com.bookstoreconsumer.adapters.input.dto.AuthorRequest;
import br.com.bookstoreconsumer.adapters.output.AuthorClientPort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AuthorClientAdapter implements AuthorClientPort {

    private final AuthorsClientApi authorsClientApi;

    public AuthorClientAdapter(AuthorsClientApi authorsClientApi) {
        this.authorsClientApi = authorsClientApi;
    }

    @Override
    public List<AuthorAndBookResponse> getAuthors(boolean books) {
        return authorsClientApi.getAuthors(books);
    }

    @Override
    public AuthorAndBookResponse getAuthorById(Long id) {
        return authorsClientApi.getAuthorById(id);
    }

    @Override
    public String saveAuthor(AuthorRequest authorRequest) {
        ResponseEntity<Void> response = authorsClientApi.saveAuthor(authorRequest);
        return Objects.requireNonNull(response.getHeaders().getLocation()).toString();
    }

    @Override
    public AuthorAndBookResponse updateAuthor(Long id, AuthorRequest authorRequest) {
        return authorsClientApi.updateAuthor(id, authorRequest);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorsClientApi.deleteAuthor(id);
    }
}
