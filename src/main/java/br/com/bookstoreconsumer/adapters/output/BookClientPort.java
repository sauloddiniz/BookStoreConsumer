package br.com.bookstoreconsumer.adapters.output;


import br.com.bookstoreconsumer.adapters.clients.dto.AuthorAndBookResponse;
import br.com.bookstoreconsumer.adapters.clients.dto.BookRequest;
import br.com.bookstoreconsumer.adapters.clients.dto.BookResponse;


public interface BookClientPort {
    AuthorAndBookResponse getListBooks(Long authorId);
    AuthorAndBookResponse getBookById(Long authorId, Long id);
    String saveBook(Long authorId, BookRequest book);
    BookResponse updateBook(Long authorId, Long id, BookRequest bookRequest);
    void deleteBook(Long authorId, Long id);
}
