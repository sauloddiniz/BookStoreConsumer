package br.com.bookstoreconsumer.adapters.clients.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record AuthorAndBookResponse(Long id,
                                    @JsonAlias({"name","nome"})
                                    @JsonProperty("nome")
                                    String name,
                                    @JsonAlias({"books","livros"})
                                    @JsonProperty("livros")
                                    List<BookResponse> books) {
}
