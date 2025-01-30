package br.com.bookstoreconsumer.adapters.output;

import br.com.bookstoreconsumer.adapters.clients.dto.AuthorAndBookResponse;
import br.com.bookstoreconsumer.adapters.input.dto.AuthorRequest;


import java.util.List;

public interface AuthorClientPort {
    List<AuthorAndBookResponse> getAuthors(boolean books);
    AuthorAndBookResponse getAuthorById(Long id);
    String saveAuthor(AuthorRequest author);
    AuthorAndBookResponse updateAuthor(Long id, AuthorRequest author);
    void deleteAuthor(Long id);
}
