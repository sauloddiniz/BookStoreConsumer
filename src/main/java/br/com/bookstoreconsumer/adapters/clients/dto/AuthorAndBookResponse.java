package br.com.bookstoreconsumer.adapters.clients.dto;

import br.com.bookstoreconsumer.core.domain.Author;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record AuthorAndBookResponse(Long id,
                                    @JsonAlias({"name","nome"})
                                    @JsonProperty("nome")
                                    String name,
                                    @JsonAlias({"books","livros"})
                                    @JsonProperty("livros")
                                    List<BookResponse> books) {

    public static Author toAuthor(AuthorAndBookResponse authorResponse) {
        return new Author(authorResponse.id(), authorResponse.name(),
                Optional.ofNullable(authorResponse.books())
                        .map(Collection::stream)
                        .orElseGet(Stream::empty)
                        .map(BookResponse::toBook)
                        .toList());
    }

    public static AuthorAndBookResponse toResponse(Author author) {
        return new AuthorAndBookResponse(author.getId(), author.getName(),
                Optional.ofNullable(author.getBooks())
                        .map(Collection::stream)
                        .orElseGet(Stream::empty)
                        .map(BookResponse::toResponse)
                        .toList());
    }
}
