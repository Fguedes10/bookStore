package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.dto.book.BookUpdateEditionDto;
import mindera.backendProject.bookStore.dto.book.BookUpdatePriceDto;
import mindera.backendProject.bookStore.exception.AuthorNotFoundException;
import mindera.backendProject.bookStore.exception.BookAlreadyExistsException;
import mindera.backendProject.bookStore.exception.BookNotFoundException;
import mindera.backendProject.bookStore.exception.PublisherNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BookService {

    List<BookGetDto> getAll();

    BookGetDto add(BookCreateDto book) throws BookAlreadyExistsException, AuthorNotFoundException, PublisherNotFoundException;

    void delete(Long id) throws BookNotFoundException;

    BookGetDto getBook(Long bookId) throws BookNotFoundException;

    BookUpdateEditionDto updateEdition(Long id, BookUpdateEditionDto book) throws BookNotFoundException;

    BookUpdatePriceDto updatePrice(Long id, BookUpdatePriceDto book) throws BookNotFoundException;

    BookGetDto getBookByTitle(String bookTitle) throws BookNotFoundException;
}
