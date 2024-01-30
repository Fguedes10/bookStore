package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.book.BookConverter;
import mindera.backendProject.bookStore.converter.customer.CustomerConverter;
import mindera.backendProject.bookStore.dto.book.*;
import mindera.backendProject.bookStore.dto.customer.CustomerGetDto;
import mindera.backendProject.bookStore.dto.customer.CustomerWhoFavoritedDto;
import mindera.backendProject.bookStore.exception.book.*;
import mindera.backendProject.bookStore.model.*;
import mindera.backendProject.bookStore.repository.bookRepository.BookRepository;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import mindera.backendProject.bookStore.service.customerService.CustomerServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AuthorServiceImpl authorServiceImpl;
    private final GenreServiceImpl genreServiceImpl;
    private final TranslationServiceImpl translationServiceImpl;
    private final ReviewServiceImpl reviewServiceImpl;
    private final PublisherServiceImpl publisherServiceImpl;

    private final CustomerRepository customerRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorServiceImpl authorServiceImpl,
                           GenreServiceImpl genreServiceImpl, TranslationServiceImpl translationServiceImpl,
                           ReviewServiceImpl reviewServiceImpl, PublisherServiceImpl publisherServiceImpl, CustomerRepository customerRepository){
        this.bookRepository = bookRepository;
        this.authorServiceImpl = authorServiceImpl;
        this.genreServiceImpl = genreServiceImpl;
        this.translationServiceImpl = translationServiceImpl;
        this.reviewServiceImpl = reviewServiceImpl;
        this.publisherServiceImpl = publisherServiceImpl;
        this.customerRepository = customerRepository;
    }


    @Override
    public List<BookGetDto> getAll() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream().map(BookConverter::fromModelToBookGetDto).toList();
    }

    @Override
    public BookGetDto add(BookCreateDto book) throws BookAlreadyExistsException, AuthorNotFoundException, PublisherNotFoundException, GenreNotFoundException, TranslationNotFoundException {
        Optional<Book> bookFindByTitle = bookRepository.findByTitle(book.title());
        Optional<Book> bookFindByIsbn = bookRepository.findByIsbn(book.isbn());
        Author author= authorServiceImpl.getAuthorById(book.authorId());
        Publisher publisher = publisherServiceImpl.getPublisherById(book.publisherId());
        List<Genre> genreList = genreServiceImpl.findByIds(book.genre());
        List<Translation> translationList = translationServiceImpl.findByIds(book.translation());
        if(bookFindByTitle.isPresent() || bookFindByIsbn.isPresent()){
            throw new BookAlreadyExistsException(BOOK_ALREADY_EXISTS);
        }
        Book newBook = BookConverter.fromCreateDtoToModel(book, author, publisher, genreList, translationList);
        reviewServiceImpl.addFirstReview(newBook);
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


    public List<Book> getBooksByIds(List<Long> books) throws BookNotFoundException {
        List<Book> bookList = bookRepository.findAllById(books);
        Set<Long> existingBookIds = bookList.stream().map(Book::getId).collect(Collectors.toSet());
        List<Long> nonExistingIds = books.stream().filter(id -> !existingBookIds.contains(id)).toList();
        if(!nonExistingIds.isEmpty()){
            throw new BookNotFoundException("Book with the id/s: " + nonExistingIds + " doesn't exist/s.");
        }
        return bookList;
    }

    public List<CustomerWhoFavoritedDto> getCustomersWhoFavorited(Long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException(BOOK_WITH_ID  + bookId + DOESNT_EXIST);
        }
        return customerRepository.findCustomersByFavoriteBook(bookId)
                .stream()
                .map(CustomerConverter::fromModelToCustomerWhoFavoritedDto)
                .toList();}
}
