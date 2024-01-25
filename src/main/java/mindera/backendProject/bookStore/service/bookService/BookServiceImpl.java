package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.BookConverter;
import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.dto.book.BookUpdateEditionDto;
import mindera.backendProject.bookStore.dto.book.BookUpdatePriceDto;
import mindera.backendProject.bookStore.exception.AuthorNotFoundException;
import mindera.backendProject.bookStore.exception.BookAlreadyExistsException;
import mindera.backendProject.bookStore.exception.BookNotFoundException;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.repository.bookRepository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AuthorServiceImpl authorServiceImpl;
    private final GenreServiceImpl genreServiceImpl;
    private final TranslationServiceImpl translationServiceImpl;
    private final ReviewServiceImpl reviewServiceImpl;
    public BookServiceImpl(BookRepository bookRepository, AuthorServiceImpl authorServiceImpl, GenreServiceImpl genreServiceImpl, TranslationServiceImpl translationServiceImpl, ReviewServiceImpl reviewServiceImpl){
        this.bookRepository = bookRepository;
        this.authorServiceImpl = authorServiceImpl;
        this.genreServiceImpl = genreServiceImpl;
        this.translationServiceImpl = translationServiceImpl;
        this.reviewServiceImpl = reviewServiceImpl;
    }


    @Override
    public List<BookGetDto> getAll() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream().map(BookConverter::fromModelToBookGetDto).toList();
    }

    @Override
    public BookGetDto add(BookCreateDto book) throws BookAlreadyExistsException, AuthorNotFoundException {
        Optional<Book> bookOptional = bookRepository.findByTitle(book.title());
        Optional<Book> bookOptional1 = bookRepository.findByIsbn(book.isbn());
        if(bookOptional.isPresent() || bookOptional1.isPresent()){
            throw new BookAlreadyExistsException(BOOK_ALREADY_EXISTS);
        }
        Book newBook = BookConverter.fromCreateDtoToModel(book, authorServiceImpl.getAuthorById(book.authorId()), genreServiceImpl.getAllGenres(), translationServiceImpl.getAllTranslations());
        newBook.setReview(reviewServiceImpl.addFirstReview());
        bookRepository.save(newBook);
        return BookConverter.fromModelToBookGetDto(newBook);
    }

    @Override
    public void delete(Long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException(BOOK_WITH_ID  + bookId + DOESNT_EXIST);
        }
        bookRepository.delete(bookOptional.get());
    }

    @Override
    public BookGetDto getBook(Long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException(BOOK_WITH_ID  + bookId + DOESNT_EXIST);
        }
        return BookConverter.fromModelToBookGetDto(bookOptional.get());
    }

    @Override
    public BookUpdateEditionDto updateEdition(Long bookId, BookUpdateEditionDto book) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException(BOOK_WITH_ID  + bookId + DOESNT_EXIST);
        }
        Book bookToUpdate =bookOptional.get();
        if(book.edition() != 0 && book.edition() != bookToUpdate.getEdition()){
            bookToUpdate.setEdition(book.edition());
        }
        Book bookToSave = bookRepository.save(bookToUpdate);
        return BookConverter.fromModelToBookUpdateEditionDto(bookToSave);
    }

    @Override
    public BookUpdatePriceDto updatePrice(Long bookId, BookUpdatePriceDto book) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException(BOOK_WITH_ID  + bookId + DOESNT_EXIST);
        }
        Book bookToUpdate = bookOptional.get();
        if(book.price() != 0 && book.price() != bookToUpdate.getPrice()){
            bookToUpdate.setPrice(book.price());
        }
      Book bookToSave = bookRepository.save(bookToUpdate);
        return BookConverter.fromModelToBookUpdatePriceDto(bookToSave);
    }

    @Override
    public BookGetDto getBookByTitle(String bookTitle) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findByTitle(bookTitle);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException(BOOK_WITH_TITLE + bookTitle + DOESNT_EXIST);
        }
        return BookConverter.fromModelToBookGetDto(bookOptional.get());
    }
}
