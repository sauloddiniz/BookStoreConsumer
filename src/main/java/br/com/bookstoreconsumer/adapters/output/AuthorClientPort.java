package br.com.bookstoreconsumer.adapters.output;

import br.com.bookstoreconsumer.core.domain.Author;

import java.util.List;

public interface AuthorClientPort {
    List<Author> getAuthors(boolean books);
    Author getAuthorById(Long id);
    String saveAuthor(Author author);
    Author updateAuthor(Long id, Author author);
    void deleteAuthor(Long id);
}
