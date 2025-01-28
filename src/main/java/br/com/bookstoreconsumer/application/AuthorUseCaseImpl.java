package br.com.bookstoreconsumer.application;

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
    public List<AuthorRequest> getAuthors() {
        return authorsClientPort.getAuthors().stream().map(AuthorRequest::toRequest).toList();
    }
}
