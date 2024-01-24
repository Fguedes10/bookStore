package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.book.BookUpdateEditionDto;
import mindera.backendProject.bookStore.dto.book.BookUpdatePriceDto;
import mindera.backendProject.bookStore.exception.BookAlreadyExistsException;
import mindera.backendProject.bookStore.exception.BookNotFoundException;

import java.util.List;

public interface BookService {

    List<BookCreateDto> getAll();

    BookCreateDto add(BookCreateDto book) throws BookAlreadyExistsException;

    void delete(Long id) throws BookNotFoundException;

    BookCreateDto getBook(Long bookId) throws BookNotFoundException;

    void updateEdition(Long id, BookUpdateEditionDto book) throws BookNotFoundException;

    void updatePrice(Long id, BookUpdatePriceDto book) throws BookNotFoundException;

    BookCreateDto getBookByTittle(String bookTittle) throws BookNotFoundException;
}
