package br.com.bookstoreconsumer.application;

import br.com.bookstoreconsumer.adapters.clients.dto.AuthorAndBookResponse;
import br.com.bookstoreconsumer.adapters.input.dto.AuthorRequest;
import br.com.bookstoreconsumer.adapters.output.AuthorClientPort;
import br.com.bookstoreconsumer.core.domain.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorUseCaseImpl implements AuthorUseCase {

    private final AuthorClientPort authorsClientPort;

    public AuthorUseCaseImpl(AuthorClientPort authorsClientPort) {
        this.authorsClientPort = authorsClientPort;
    }

    @Override
    public List<AuthorAndBookResponse> getAuthors(boolean books) {
        List<Author> authors = authorsClientPort.getAuthors(books);
        return authors.stream().map(AuthorAndBookResponse::toResponse).toList();
    }

    @Override
    public AuthorAndBookResponse getAuthorById(Long id) {
        Author author = authorsClientPort.getAuthorById(id);
        return AuthorAndBookResponse.toResponse(author);
    }

    @Override
    public String saveAuthor(AuthorRequest authorRequest) {
        Author author = new Author(authorRequest.name());
        String location = authorsClientPort.saveAuthor(author);
        return location.substring(location.lastIndexOf("/") + 1);
    }

    @Override
    public AuthorAndBookResponse updateAuthor(Long id, AuthorRequest author) {
        Author authorToUpdate = new Author(id, author.name());
        authorToUpdate = authorsClientPort.updateAuthor(id, authorToUpdate);
        return AuthorAndBookResponse.toResponse(authorToUpdate);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorsClientPort.deleteAuthor(id);
    }
}
