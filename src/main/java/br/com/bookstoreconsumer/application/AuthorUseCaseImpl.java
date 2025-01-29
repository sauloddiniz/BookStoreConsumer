package br.com.bookstoreconsumer.application;

import br.com.bookstoreconsumer.adapters.clients.dto.AuthorResponse;
import br.com.bookstoreconsumer.adapters.input.dto.AuthorRequest;
import br.com.bookstoreconsumer.adapters.output.AuthorsClientPort;
import br.com.bookstoreconsumer.core.domain.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorUseCaseImpl implements AuthorUseCase {

    private final AuthorsClientPort authorsClientPort;

    public AuthorUseCaseImpl(AuthorsClientPort authorsClientPort) {
        this.authorsClientPort = authorsClientPort;
    }

    @Override
    public List<AuthorResponse> getAuthors() {
        List<Author> authors = authorsClientPort.getAuthors();
        return authors.stream().map(AuthorResponse::toResponse).toList();
    }

    @Override
    public AuthorResponse getAuthorById(Long id) {
        Author author = authorsClientPort.getAuthorById(id);
        return AuthorResponse.toResponse(author);
    }

    @Override
    public String saveAuthor(AuthorRequest authorRequest) {
        Author author = new Author(authorRequest.name());
        return authorsClientPort.saveAuthor(author);
    }

    @Override
    public AuthorResponse updateAuthor(Long id, AuthorRequest author) {
        Author authorToUpdate = new Author(id, author.name());
        authorToUpdate = authorsClientPort.updateAuthor(id, authorToUpdate);
        return AuthorResponse.toResponse(authorToUpdate);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorsClientPort.deleteAuthor(id);
    }
}
