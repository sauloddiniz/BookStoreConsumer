package br.com.bookstoreconsumer.adapters.clients.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record BookRequest(
        Long id,
        @JsonAlias({"title", "titulo"})
        @JsonProperty("title")
        String title,
        @JsonAlias({"description","descricao"})
        @JsonProperty("description")
        String description,
        @JsonAlias({"category","categoria"})
        @JsonProperty("category")
        String category) {
}
