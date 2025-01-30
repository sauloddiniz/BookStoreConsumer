package br.com.bookstoreconsumer.adapters.input.dto;

import br.com.bookstoreconsumer.core.domain.Author;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthorRequest(
        @JsonAlias({"name","nome"})
        @JsonProperty("name")
        String name) {

    public static AuthorRequest toRequest(Author author) {
        return new AuthorRequest(author.getName());
    }
}
