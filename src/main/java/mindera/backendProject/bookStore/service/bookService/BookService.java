package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.dto.book.BookUpdateEditionDto;
import mindera.backendProject.bookStore.dto.book.BookUpdatePriceDto;
import mindera.backendProject.bookStore.exception.*;
import org.springframework.http.HttpStatusCode;

import java.util.List;


public interface BookService {

    List<BookGetDto> getAll();

    BookGetDto add(BookCreateDto book) throws BookAlreadyExistsException, AuthorNotFoundException, PublisherNotFoundException, GenreNotFoundException, TranslationNotFoundException;

    void delete(Long id) throws BookNotFoundException;

    BookGetDto getBook(Long bookId) throws BookNotFoundException;

    BookUpdateEditionDto updateEdition(Long id, BookUpdateEditionDto book) throws BookNotFoundException;

    BookUpdatePriceDto updatePrice(Long id, BookUpdatePriceDto book) throws BookNotFoundException;

    BookGetDto getBookByTitle(String bookTitle) throws BookNotFoundException;

}
