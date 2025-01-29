package br.com.bookstoreconsumer.application;

import br.com.bookstoreconsumer.adapters.clients.dto.AuthorResponse;
import br.com.bookstoreconsumer.adapters.input.dto.AuthorRequest;

import java.util.List;

public interface AuthorUseCase {
    List<AuthorResponse> getAuthors();
    AuthorResponse getAuthorById(Long id);
    String saveAuthor(AuthorRequest author);
    AuthorResponse updateAuthor(Long id, AuthorRequest author);
    void deleteAuthor(Long id);
}
