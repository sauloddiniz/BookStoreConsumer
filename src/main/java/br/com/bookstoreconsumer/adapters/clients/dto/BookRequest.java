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

    public static BookRequest toResponse(br.com.bookstoreconsumer.core.domain.Book book) {
        return new BookRequest(book.getId(), book.getTitle(), book.getDescription(), book.getCategory());
    }

    public static br.com.bookstoreconsumer.core.domain.Book toBook(BookRequest bookResponse) {
        return new br.com.bookstoreconsumer.core.domain.Book(bookResponse.id(), bookResponse.title(), bookResponse.description(), bookResponse.category());
    }
}
