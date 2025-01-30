package br.com.bookstoreconsumer.adapters.clients.dto;

import br.com.bookstoreconsumer.core.domain.Author;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public record AuthorResponse(Long id,
                             @JsonAlias("name")
                             @JsonProperty("nome")
                             String name,
                             @JsonAlias("books")
                             @JsonProperty("livros")
                             List<BookResponse> books) {

    public static Author toAuthor(AuthorResponse authorResponse) {
        return new Author(authorResponse.id(), authorResponse.name(),
                Optional.ofNullable(authorResponse.books())
                        .map(Collection::stream)
                        .orElseGet(Stream::empty)
                        .map(BookResponse::toBook)
                        .toList());
    }

    public static AuthorResponse toResponse(Author author) {
        return new AuthorResponse(author.getId(), author.getName(),
                Optional.ofNullable(author.getBooks())
                        .map(Collection::stream)
                        .orElseGet(Stream::empty)
                        .map(BookResponse::toResponse)
                        .toList());
    }
}
