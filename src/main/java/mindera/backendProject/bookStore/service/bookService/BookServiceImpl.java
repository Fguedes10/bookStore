package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.book.BookUpdateEditionDto;
import mindera.backendProject.bookStore.dto.book.BookUpdatePriceDto;
import mindera.backendProject.bookStore.exception.BookAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{


    @Override
    public List<BookCreateDto> getAll() {
        return null;
    }

    @Override
    public BookCreateDto add(BookCreateDto book) throws BookAlreadyExistsException {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public BookCreateDto getBook(Long bookId) {
        return null;
    }

    @Override
    public void updateEdition(Long id, BookUpdateEditionDto book) {

    }

    @Override
    public void updatePrice(Long id, BookUpdatePriceDto book) {

    }

    @Override
    public BookCreateDto getBookByTittle(String bookTittle) {
        return null;
    }
}
