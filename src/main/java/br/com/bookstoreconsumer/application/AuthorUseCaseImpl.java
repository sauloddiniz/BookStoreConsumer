package br.com.bookstoreconsumer.application;

import br.com.bookstoreconsumer.adapters.clients.dto.AuthorAndBookResponse;
import br.com.bookstoreconsumer.adapters.input.dto.AuthorRequest;
import br.com.bookstoreconsumer.adapters.output.AuthorClientPort;
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
        return authorsClientPort.getAuthors(books);
    }

    @Override
    public AuthorAndBookResponse getAuthorById(Long id) {
        return authorsClientPort.getAuthorById(id);
    }

    @Override
    public String saveAuthor(AuthorRequest authorRequest) {
        String location = authorsClientPort.saveAuthor(authorRequest);
        return location.substring(location.lastIndexOf("/") + 1);
    }

    @Override
    public AuthorAndBookResponse updateAuthor(Long id, AuthorRequest author) {
        return authorsClientPort.updateAuthor(id, author);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorsClientPort.deleteAuthor(id);
    }
}
