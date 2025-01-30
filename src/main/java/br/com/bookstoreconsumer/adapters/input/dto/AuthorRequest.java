package br.com.bookstoreconsumer.adapters.input.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthorRequest(
        @JsonAlias({"name","nome"})
        @JsonProperty("name")
        String name) {
}
