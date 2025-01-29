package br.com.bookstoreconsumer.adapters.clients.dto;

import br.com.bookstoreconsumer.core.domain.Author;

public record AuthorResponse(Long id, String name) {

    public static Author toAuthor(AuthorResponse authorResponse) {
        return new Author(authorResponse.id(), authorResponse.name());
    }

    public static AuthorResponse toResponse(Author author) {
        return new AuthorResponse(author.getId(), author.getName());
    }
}
