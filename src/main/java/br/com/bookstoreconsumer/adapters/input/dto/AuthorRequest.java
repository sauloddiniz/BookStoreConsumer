package br.com.bookstoreconsumer.adapters.input.dto;

import br.com.bookstoreconsumer.core.domain.Author;

public record AuthorRequest(Long id, String name) {

    public static AuthorRequest toRequest(Author author) {
        return new AuthorRequest(author.getId(), author.getName());
    }
}
