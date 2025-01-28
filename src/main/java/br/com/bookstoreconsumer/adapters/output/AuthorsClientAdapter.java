package br.com.bookstoreconsumer.adapters.output;

import br.com.bookstoreconsumer.adapters.clients.AuthorsClientApi;
import br.com.bookstoreconsumer.adapters.clients.dto.AuthorResponse;
import br.com.bookstoreconsumer.core.domain.Author;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorsClientAdapter implements AuthorsClientPort {

    private final AuthorsClientApi authorsClientApi;

    public AuthorsClientAdapter(AuthorsClientApi authorsClientApi) {
        this.authorsClientApi = authorsClientApi;
    }

    @Override
    public List<Author> getAuthors() {
        List<AuthorResponse> authorRequests = authorsClientApi.getAuthors().getBody();
        return authorRequests.stream().map(AuthorResponse::toAuthor).toList();
    }

    @Override
    public Author getAuthorById(Long id) {
        AuthorResponse authorResponse = authorsClientApi.getAuthorById(id).getBody();
        return AuthorResponse.toAuthor(authorResponse);
    }
}
