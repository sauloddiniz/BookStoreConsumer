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

    public static BookResponse toResponse(br.com.bookstoreconsumer.core.domain.Book book) {
        return new BookResponse(book.getId(), book.getTitle(), book.getDescription(), book.getCategory());
    }

    public static br.com.bookstoreconsumer.core.domain.Book toBook(BookResponse bookResponse) {
        return new br.com.bookstoreconsumer.core.domain.Book(bookResponse.id(), bookResponse.title(), bookResponse.description(), bookResponse.category());
    }
}
