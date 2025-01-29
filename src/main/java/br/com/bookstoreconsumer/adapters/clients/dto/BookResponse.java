package br.com.bookstoreconsumer.adapters.clients.dto;


import br.com.bookstoreconsumer.core.domain.Book;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;


public record BookResponse(
        Long id,
        @JsonAlias("title")
        @JsonProperty("titulo")
        String title,
        @JsonAlias("description")
        @JsonProperty("descricao")
        String description,
        @JsonAlias("category")
        @JsonProperty("categoria")
        String category) {

    public static BookResponse toResponse(Book book) {
        return new BookResponse(book.getId(), book.getTitle(), book.getDescription(), book.getCategory());
    }

    public static Book toBook(BookResponse bookResponse) {
        return new Book(bookResponse.id(), bookResponse.title(), bookResponse.description(), bookResponse.category());
    }
}
