package br.com.bookstoreconsumer.adapters.output;

import br.com.bookstoreconsumer.core.domain.Author;

import java.util.List;

public interface AuthorsClientPort {
    List<Author> getAuthors();
}
