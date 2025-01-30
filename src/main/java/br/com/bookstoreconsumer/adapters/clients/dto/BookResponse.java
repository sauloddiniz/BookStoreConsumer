package br.com.bookstoreconsumer.adapters.clients.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record BookResponse(
        Long id,
        @JsonAlias({"title", "titulo"})
        @JsonProperty("titulo")
        String title,
        @JsonAlias({"description","descricao"})
        @JsonProperty("descricao")
        String description,
        @JsonAlias({"category","categoria"})
        @JsonProperty("categoria")
        String category) {

}
