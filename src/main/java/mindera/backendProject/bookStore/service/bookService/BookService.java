package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.*;
import mindera.backendProject.bookStore.exception.book.*;

import java.util.List;


public interface BookService {

    List<BookGetDto> getAll(int page, int size, String searchTerm);

    BookGetNewBookDto add(BookCreateDto book) throws BookAlreadyExistsException, AuthorNotFoundException,
            PublisherNotFoundException, GenreNotFoundException, TranslationNotFoundException;

    void delete(Long id) throws BookNotFoundException;

    BookGetDto getBook(Long bookId) throws BookNotFoundException;

    BookUpdateEditionDto updateEdition(Long id, BookUpdateEditionDto book) throws BookNotFoundException;

    BookUpdatePriceDto updatePrice(Long id, BookUpdatePriceDto book) throws BookNotFoundException;

    BookGetDto getBookByTitle(String bookTitle) throws BookNotFoundException;

}
