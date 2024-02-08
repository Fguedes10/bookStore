package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.book.BookConverter;
import mindera.backendProject.bookStore.converter.customer.CustomerConverter;
import mindera.backendProject.bookStore.dto.book.*;
import mindera.backendProject.bookStore.dto.customer.CustomerWhoFavoritedDto;
import mindera.backendProject.bookStore.exception.book.*;
import mindera.backendProject.bookStore.googleBooksApi.GoogleBookInfoDto;
import mindera.backendProject.bookStore.googleBooksApi.GoogleBooksService;
import mindera.backendProject.bookStore.model.*;
import mindera.backendProject.bookStore.repository.bookRepository.BookRepository;
import mindera.backendProject.bookStore.repository.bookRepository.TranslationRepository;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorServiceImpl authorServiceImpl;

    private final GenreServiceImpl genreServiceImpl;

    private final TranslationServiceImpl translationServiceImpl;

    private final ReviewServiceImpl reviewServiceImpl;

    private final PublisherServiceImpl publisherServiceImpl;

    private final TranslationRepository translationRepository;

    private final GoogleBooksService googleBooksService;

    private final CustomerRepository customerRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorServiceImpl authorServiceImpl, GenreServiceImpl genreServiceImpl, TranslationServiceImpl translationServiceImpl, ReviewServiceImpl reviewServiceImpl, PublisherServiceImpl publisherServiceImpl, TranslationRepository translationRepository, GoogleBooksService googleBooksService, CustomerRepository customerRepository) {
        this.bookRepository = bookRepository;
        this.authorServiceImpl = authorServiceImpl;
        this.genreServiceImpl = genreServiceImpl;
        this.translationServiceImpl = translationServiceImpl;
        this.reviewServiceImpl = reviewServiceImpl;
        this.publisherServiceImpl = publisherServiceImpl;
        this.translationRepository = translationRepository;
        this.googleBooksService = googleBooksService;
        this.customerRepository = customerRepository;
    }


    @Override
    public List<BookGetDto> getAll(int page, int size, String searchTerm) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, searchTerm);
        Page<Book> bookList = bookRepository.findAll(pageRequest);
        return bookList.stream().map(BookConverter::fromModelToBookGetDto).toList();
    }

    @Override
    public BookGetNewBookDto add(BookCreateDto book) throws BookAlreadyExistsException, AuthorNotFoundException,
            PublisherNotFoundException, GenreNotFoundException, TranslationNotFoundException {
        Optional<Book> bookFindByTitle = bookRepository.findByTitle(book.title());
        Optional<Book> bookFindByIsbn = bookRepository.findByIsbn(book.isbn());
        Author author = authorServiceImpl.getAuthorById(book.authorId());
        Publisher publisher = publisherServiceImpl.getPublisherById(book.publisherId());
        List<Genre> genreList = genreServiceImpl.findByIds(book.genre());
        List<Translation> translationList = translationServiceImpl.findByIds(book.translation());
        if (bookFindByTitle.isPresent() || bookFindByIsbn.isPresent()) {
            throw new BookAlreadyExistsException(BOOK_ALREADY_EXISTS);
        }
        Book newBook = BookConverter.fromCreateDtoToModel(book, author, publisher, genreList, translationList);
        String title = newBook.getTitle().replaceAll("\\s+", "");
        GoogleBookInfoDto googleBook = googleBooksService.getBookInfo(title);
        if (googleBook != null) {
            newBook.setRating(googleBook.getAverageRating());
            newBook.setPageCount(googleBook.getPageCount());
        }
        bookRepository.save(newBook);
        reviewServiceImpl.addFirstReview(newBook);
        return BookConverter.fromModelToBookGetNewBookDto(newBook);
    }

    @Override
    public void delete(Long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException(BOOK_WITH_ID + bookId + DOESNT_EXIST);
        }
        bookRepository.delete(bookOptional.get());
    }

    @Override
    public BookGetDto getBook(Long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException(BOOK_WITH_ID + bookId + DOESNT_EXIST);
        }
        return BookConverter.fromModelToBookGetDto(bookOptional.get());
    }

    @Override
    public BookUpdateEditionDto updateEdition(Long bookId, BookUpdateEditionDto book) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException(BOOK_WITH_ID + bookId + DOESNT_EXIST);
        }
        Book bookToUpdate = bookOptional.get();
        if (book.edition() != 0 && book.edition() != bookToUpdate.getEdition()) {
            bookToUpdate.setEdition(book.edition());
        }
        Book bookToSave = bookRepository.save(bookToUpdate);
        return BookConverter.fromModelToBookUpdateEditionDto(bookToSave);
    }

    @Override
    public BookUpdatePriceDto updatePrice(Long bookId, BookUpdatePriceDto book) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException(BOOK_WITH_ID + bookId + DOESNT_EXIST);
        }
        Book bookToUpdate = bookOptional.get();
        if (book.price() != 0 && book.price() != bookToUpdate.getPrice()) {
            bookToUpdate.setPrice(book.price());
        }
        Book bookToSave = bookRepository.save(bookToUpdate);
        return BookConverter.fromModelToBookUpdatePriceDto(bookToSave);
    }

    @Override
    public BookGetDto getBookByTitle(String bookTitle) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findByTitle(bookTitle);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException(BOOK_WITH_TITLE + bookTitle + DOESNT_EXIST);
        }
        return BookConverter.fromModelToBookGetDto(bookOptional.get());
    }


    public List<Book> getBooksByIds(List<Long> books) throws BookNotFoundException {
        List<Book> bookList = bookRepository.findAllById(books);
        Set<Long> existingBookIds = bookList.stream().map(Book::getId).collect(Collectors.toSet());
        List<Long> nonExistingIds = books.stream().filter(id -> !existingBookIds.contains(id)).toList();
        if (!nonExistingIds.isEmpty()) {
            throw new BookNotFoundException("Book with the id/s: " + nonExistingIds + " doesn't exist/s.");
        }
        return bookList;
    }

    public List<CustomerWhoFavoritedDto> getCustomersWhoFavorited(Long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException(BOOK_WITH_ID + bookId + DOESNT_EXIST);
        }
        return customerRepository.findCustomersByFavoriteBook(bookId)
                .stream()
                .map(CustomerConverter::fromModelToCustomerWhoFavoritedDto)
                .toList();
    }

    public List<BookYearReleaseInfoDto> getBooksByYearRelease(int releaseYear) throws IncorrectReleaseYearException {
        if (releaseYear <= 0) {
            throw new IncorrectReleaseYearException("Incorrect release year: " + releaseYear);
        }
        List<BookYearReleaseInfoDto> findedBooks = bookRepository.findBooksByYearRelease(releaseYear)
                .stream()
                .map(BookConverter::fromModelToBookYearReleaseInfoDto)
                .toList();
        if (findedBooks.isEmpty()) {
            throw new IncorrectReleaseYearException("No books with release year: " + releaseYear);
        }
        return findedBooks;
    }

    public List<BookGetByTranslationDto> getBooksByTranslation(Long translationId) throws TranslationNotFoundException {
        if (translationId <= 0) {
            throw new TranslationNotFoundException(TRANSLATION_WITH_ID + translationId + DOESNT_EXIST);
        }
        Optional<Translation> getTranslation = translationRepository.findById(translationId);
        if (getTranslation.isEmpty()) {
            throw new TranslationNotFoundException(TRANSLATION_WITH_ID + translationId + DOESNT_EXIST);
        }

        List<Book> findedBooks = bookRepository.findBooksByTranslation(translationId);
        if (findedBooks.isEmpty()) {
            throw new TranslationNotFoundException("No books with translation: " + translationId);
        }
        return bookRepository.findBooksByTranslation(translationId)
                .stream()
                .map(BookConverter::fromModelToBookGetByTranslationDto)
                .toList();
    }


}
