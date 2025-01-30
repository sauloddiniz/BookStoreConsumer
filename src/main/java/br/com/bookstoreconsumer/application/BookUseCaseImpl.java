package br.com.bookstoreconsumer.application;

import br.com.bookstoreconsumer.adapters.clients.dto.AuthorAndBookResponse;
import br.com.bookstoreconsumer.adapters.clients.dto.BookRequest;
import br.com.bookstoreconsumer.adapters.clients.dto.BookResponse;
import br.com.bookstoreconsumer.adapters.output.BookClientPort;
import org.springframework.stereotype.Service;

@Service
public class BookUseCaseImpl implements BookUseCase {

    private final BookClientPort bookClientPort;

    public BookUseCaseImpl(BookClientPort bookClientPort) {
        this.bookClientPort = bookClientPort;
    }

    @Override
    public AuthorAndBookResponse getListBooks(Long authorId) {
        return bookClientPort.getListBooks(authorId);
    }

    @Override
    public AuthorAndBookResponse getBookById(Long authorId, Long id) {
        return bookClientPort.getBookById(authorId, id);
    }

    @Override
    public String saveBook(Long authorId, BookRequest book) {
        String location = bookClientPort.saveBook(authorId, book);
        return location.substring(location.lastIndexOf("/") + 1);
    }

    @Override
    public BookResponse updateBook(Long authorId, Long id, BookRequest bookRequest) {
        return bookClientPort.updateBook(authorId, id, bookRequest);
    }

    @Override
    public void deleteBook(Long authorId, Long id) {
        bookClientPort.deleteBook(authorId, id);
    }
}
