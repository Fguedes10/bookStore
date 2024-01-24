package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.BookConverter;
import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.book.BookUpdateEditionDto;
import mindera.backendProject.bookStore.dto.book.BookUpdatePriceDto;
import mindera.backendProject.bookStore.exception.BookAlreadyExistsException;
import mindera.backendProject.bookStore.exception.BookNotFoundException;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.repository.bookRepository.BookRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }


    @Override
    public List<BookCreateDto> getAll() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream().map(BookConverter::fromModelToBookCreateDto).toList();
    }

    @Override
    public BookCreateDto add(BookCreateDto book) throws BookAlreadyExistsException {
        if(bookRepository.findByTittle(book.title()).isPresent() || bookRepository.findByIsbn(book.isbn()).isPresent()){
            throw new BookAlreadyExistsException("Book already exists");
        }
        Book newBook = BookConverter.fromCreateDtoToModel(book);
        return book;
    }

    @Override
    public void delete(Long bookId) throws BookNotFoundException {
        bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book with id" + bookId + "does not exists"));
        bookRepository.deleteById(bookId);
    }

    @Override
    public BookCreateDto getBook(Long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("Book with id" + bookId + "does not exists");
        }
        return BookConverter.fromModelToBookCreateDto(bookOptional.get());
    }

    @Override
    public void updateEdition(Long bookId, BookUpdateEditionDto book) throws BookNotFoundException {
        Book updatedBook = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book with id" + bookId + "does not exists"));
        updatedBook.setEdition(book.edition());
        bookRepository.save(updatedBook);
    }

    @Override
    public void updatePrice(Long bookId, BookUpdatePriceDto book) throws BookNotFoundException {
        Book updatedBook = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book with id" + bookId + "does not exists"));
        updatedBook.setPrice(book.price());
        bookRepository.save(updatedBook);
    }

    @Override
    public BookCreateDto getBookByTittle(String bookTittle) throws BookNotFoundException {
        return (BookCreateDto) bookRepository.findByTittle(bookTittle).orElseThrow(() -> new BookNotFoundException("Book with tittle" + bookTittle + "does not exists"));
    }
}
