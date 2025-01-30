package br.com.bookstoreconsumer.adapters;

import br.com.bookstoreconsumer.adapters.clients.BookClientApi;
import br.com.bookstoreconsumer.adapters.clients.dto.AuthorAndBookResponse;
import br.com.bookstoreconsumer.adapters.clients.dto.BookRequest;
import br.com.bookstoreconsumer.adapters.clients.dto.BookResponse;
import br.com.bookstoreconsumer.adapters.output.BookClientPort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BookClientAdapter implements BookClientPort {

    private final BookClientApi bookClientApi;

    public BookClientAdapter(BookClientApi bookClientApi) {
        this.bookClientApi = bookClientApi;
    }

    @Override
    public AuthorAndBookResponse getListBooks(Long authorId) {
        return bookClientApi.getBooks(authorId).getBody();
    }

    @Override
    public AuthorAndBookResponse getBookById(Long authorId, Long id) {
        return bookClientApi.getBookById(authorId, id).getBody();
    }

    @Override
    public String saveBook(Long authorId, BookRequest book) {
        ResponseEntity<Void> response = bookClientApi.saveBook(authorId, book);
        return Objects.requireNonNull(response.getHeaders().getLocation()).toString();
    }

    @Override
    public BookResponse updateBook(Long authorId, Long id, BookRequest bookRequest) {
        return bookClientApi.updateBook(authorId, id, bookRequest).getBody();
    }

    @Override
    public void deleteBook(Long authorId, Long id) {
        bookClientApi.deleteBook(authorId, id);
    }
}
