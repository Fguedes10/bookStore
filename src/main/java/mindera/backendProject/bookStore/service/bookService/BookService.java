package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dtos.books.BookCreateDto;
import mindera.backendProject.bookStore.dtos.books.BookUpdateEditionDto;
import mindera.backendProject.bookStore.dtos.books.BookUpdatePriceDto;
import mindera.backendProject.bookStore.exception.BookAlreadyExistsException;

import java.util.List;

public interface BookService {

    List<BookCreateDto> getAll();

    BookCreateDto add(BookCreateDto book) throws BookAlreadyExistsException;

    void delete(Long id);

    BookCreateDto getBook(Long bookId);

    void updateEdition(Long id, BookUpdateEditionDto book);

    void updatePrice(Long id, BookUpdatePriceDto book);

    BookCreateDto getBookByTittle(String bookTittle);
}
