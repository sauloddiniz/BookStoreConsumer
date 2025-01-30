package br.com.bookstoreconsumer.adapters;

import br.com.bookstoreconsumer.adapters.clients.AuthorsClientApi;
import br.com.bookstoreconsumer.adapters.clients.dto.AuthorAndBookResponse;
import br.com.bookstoreconsumer.adapters.input.dto.AuthorRequest;
import br.com.bookstoreconsumer.adapters.output.AuthorClientPort;
import br.com.bookstoreconsumer.core.domain.Author;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorClientAdapter implements AuthorClientPort {

    private final AuthorsClientApi authorsClientApi;

    public AuthorClientAdapter(AuthorsClientApi authorsClientApi) {
        this.authorsClientApi = authorsClientApi;
    }

    @Override
    public List<Author> getAuthors(boolean books) {
        List<AuthorAndBookResponse> authorRequests = authorsClientApi.getAuthors(books).getBody();
        return authorRequests.stream().map(AuthorAndBookResponse::toAuthor).toList();
    }

    @Override
    public Author getAuthorById(Long id) {
        AuthorAndBookResponse authorResponse = authorsClientApi.getAuthorById(id).getBody();
        return AuthorAndBookResponse.toAuthor(authorResponse);
    }

    @Override
    public String saveAuthor(Author author) {
        AuthorRequest authorRequest = AuthorRequest.toRequest(author);
        ResponseEntity<Void> response = authorsClientApi.saveAuthor(authorRequest);
        return response.getHeaders().getLocation().toString();
    }

    @Override
    public Author updateAuthor(Long id, Author author) {
        AuthorRequest authorRequest = AuthorRequest.toRequest(author);
        AuthorAndBookResponse authorResponse = authorsClientApi.updateAuthor(id, authorRequest).getBody();
        return AuthorAndBookResponse.toAuthor(authorResponse);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorsClientApi.deleteAuthor(id);
    }
}
