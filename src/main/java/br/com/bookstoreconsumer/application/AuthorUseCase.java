package br.com.bookstoreconsumer.application;

import br.com.bookstoreconsumer.adapters.input.dto.AuthorRequest;

import java.util.List;

public interface AuthorUseCase {
    List<AuthorRequest> getAuthors();
    AuthorRequest getAuthorById(Long id);
}
