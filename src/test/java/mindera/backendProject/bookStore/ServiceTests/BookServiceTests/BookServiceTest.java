package mindera.backendProject.bookStore.ServiceTests.BookServiceTests;

import mindera.backendProject.bookStore.converter.book.*;
import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.book.BookGetByTranslationDto;
import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.dto.book.BookYearReleaseInfoDto;
import mindera.backendProject.bookStore.exception.book.BookAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.book.IncorrectReleaseYearException;
import mindera.backendProject.bookStore.exception.book.TranslationNotFoundException;
import mindera.backendProject.bookStore.googleBooksApi.GoogleBooksService;
import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Publisher;
import mindera.backendProject.bookStore.model.Translation;
import mindera.backendProject.bookStore.repository.bookRepository.BookRepository;
import mindera.backendProject.bookStore.repository.bookRepository.TranslationRepository;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import mindera.backendProject.bookStore.service.bookService.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    static MockedStatic<BookConverter> mockedBookConverter = Mockito.mockStatic(BookConverter.class);
    @MockBean
    private BookRepository bookRepositoryMock;
    @MockBean
    private AuthorServiceImpl authorService;
    @MockBean
    private GenreServiceImpl genreService;
    @MockBean
    private PublisherServiceImpl publisherService;
    @MockBean
    private ReviewServiceImpl reviewService;
    @MockBean
    private TranslationRepository translationRepositoryMock;
    @MockBean
    private TranslationServiceImpl translationService;
    @MockBean
    private CustomerRepository customerRepositoryMock;
    @MockBean
    private GoogleBooksService googleBooksService;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookServiceImpl(bookRepositoryMock, authorService, genreService, translationService, reviewService, publisherService, translationRepositoryMock, googleBooksService, customerRepositoryMock);
    }


    @Test
    @DisplayName("Get all books and check repository")
    void testGetAll() {

        // GIVEN
        int page = 0;
        int size = 10;
        String searchTerm = "title";

        Book book1 = Book.builder()
                .id(1L)
                .title("A criada")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .genre(new ArrayList<>())
                .translation(new ArrayList<>())
                .edition(1)
                .yearRelease(2022)
                .price(9.99)
                .rating(4.5)
                .pageCount(200)
                .review(new ArrayList<>()).build();

        Book book2 = Book.builder()
                .id(2L)
                .title("A familia perfeita")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .genre(new ArrayList<>())
                .translation(new ArrayList<>())
                .edition(2)
                .yearRelease(2023)
                .price(10.00)
                .rating(4.0)
                .pageCount(350)
                .review(new ArrayList<>()).build();

        List<Book> BookList = Arrays.asList(book1, book2);
        Page<Book> mockedPage = new PageImpl<>(BookList);

        when(bookRepositoryMock.findAll(any(PageRequest.class))).thenReturn(mockedPage);

        mockedBookConverter.when(() -> BookConverter.fromModelToBookGetDto(book1)).thenReturn(new BookGetDto(
                book1.getTitle(),
                AuthorConverter.fromModelToAuthorCreateDto(book1.getAuthor()),
                PublisherConverter.fromModelToPublisherCreateDto(book1.getPublisher()),
                book1.getGenre().stream().map(GenreConverter::fromModelToGenreCreateDto).toList(),
                book1.getTranslation().stream().map(TranslationConverter::fromModelToTranslationCreateDto).toList(),
                book1.getEdition(),
                book1.getYearRelease(),
                book1.getPrice(),
                book1.getRating(),
                book1.getPageCount(),
                book1.getReview().stream().map(ReviewConverter::fromModelToReviewCreateDto).toList()));

        mockedBookConverter.when(() -> BookConverter.fromModelToBookGetDto(book2)).thenReturn(new BookGetDto(
                book2.getTitle(),
                AuthorConverter.fromModelToAuthorCreateDto(book2.getAuthor()),
                PublisherConverter.fromModelToPublisherCreateDto(book2.getPublisher()),
                book2.getGenre().stream().map(GenreConverter::fromModelToGenreCreateDto).toList(),
                book2.getTranslation().stream().map(TranslationConverter::fromModelToTranslationCreateDto).toList(),
                book2.getEdition(),
                book2.getYearRelease(),
                book2.getPrice(),
                book2.getRating(),
                book1.getPageCount(),
                book2.getReview().stream().map(ReviewConverter::fromModelToReviewCreateDto).toList()));


        // WHEN
        List<BookGetDto> result = bookService.getAll(page, size, searchTerm);

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(bookRepositoryMock).findAll(PageRequest.of(page, size, Sort.Direction.DESC, searchTerm));
        mockedBookConverter.verify(() -> BookConverter.fromModelToBookGetDto(book1));
        mockedBookConverter.verify(() -> BookConverter.fromModelToBookGetDto(book2));


    }

    @Test
    @DisplayName("Get book by id and check repository")
    void testGetBook() throws BookNotFoundException {

        //GIVEN
        Long bookId = 1L;

        Book book = Book.builder()
                .id(1L)
                .title("A criada")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .genre(new ArrayList<>())
                .translation(new ArrayList<>())
                .edition(1)
                .yearRelease(2022)
                .price(9.99)
                .rating(4.5)
                .pageCount(200)
                .review(new ArrayList<>()).build();


        when(bookRepositoryMock.findById(bookId)).thenReturn(Optional.of(book));
        BookGetDto expectedDto = new BookGetDto(
                book.getTitle(),
                AuthorConverter.fromModelToAuthorCreateDto(book.getAuthor()),
                PublisherConverter.fromModelToPublisherCreateDto(book.getPublisher()),
                book.getGenre().stream().map(GenreConverter::fromModelToGenreCreateDto).toList(),
                book.getTranslation().stream().map(TranslationConverter::fromModelToTranslationCreateDto).toList(),
                book.getEdition(),
                book.getYearRelease(),
                book.getPrice(), book.getRating(),
                book.getPageCount(),
                book.getReview().stream().map(ReviewConverter::fromModelToReviewCreateDto).toList());

        when(BookConverter.fromModelToBookGetDto(book)).thenReturn(new BookGetDto(
                book.getTitle(),
                AuthorConverter.fromModelToAuthorCreateDto(book.getAuthor()),
                PublisherConverter.fromModelToPublisherCreateDto(book.getPublisher()),
                book.getGenre().stream().map(GenreConverter::fromModelToGenreCreateDto).toList(),
                book.getTranslation().stream().map(TranslationConverter::fromModelToTranslationCreateDto).toList(),
                book.getEdition(),
                book.getYearRelease(),
                book.getPrice(),
                book.getRating(),
                book.getPageCount(),
                book.getReview().stream().map(ReviewConverter::fromModelToReviewCreateDto).toList()));

        // WHEN
        BookGetDto resultDto = bookService.getBook(bookId);

        // THEN
        assertEquals(expectedDto, resultDto);
        verify(bookRepositoryMock, times(1)).findById(bookId);
        mockedBookConverter.verify(() -> BookConverter.fromModelToBookGetDto(book));
    }

    @Test
    @DisplayName("Add a book and check repository")
    void testAddBookSuccessfully() {

        // GIVEN
        BookCreateDto bookExistingDto = new BookCreateDto(
                "A criada",
                478521L,
                1L,
                1L,
                List.of(1L),
                List.of(1L),
                1,
                2022,
                5.99);

        when(bookRepositoryMock.findByTitle(bookExistingDto.title())).thenReturn(Optional.of(new Book()));
        when(bookRepositoryMock.findByIsbn(bookExistingDto.isbn())).thenReturn(Optional.of(new Book()));

        // WHEN / THEN
        assertThrows(BookAlreadyExistsException.class, () -> {
            bookService.add(bookExistingDto);
        });
        verify(bookRepositoryMock, never()).save(any(Book.class));

    }

    @Test
    @DisplayName("Get books by Id's and check exception")
    void testGetBooksByIds() throws BookNotFoundException {

        // GIVEN
        List<Long> bookIds = Arrays.asList(1L, 2L);
        Book book1 = Book.builder()
                .id(1L)
                .title("A criada")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .genre(new ArrayList<>())
                .translation(new ArrayList<>())
                .edition(1)
                .yearRelease(2022)
                .price(9.99)
                .rating(4.5)
                .pageCount(200)
                .review(new ArrayList<>()).build();

        Book book2 = Book.builder()
                .id(2L)
                .title("A familia perfeita")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .genre(new ArrayList<>())
                .translation(new ArrayList<>())
                .edition(2)
                .yearRelease(2022)
                .price(10.00)
                .rating(4.0)
                .pageCount(350)
                .review(new ArrayList<>()).build();

        List<Book> existingBooks = Arrays.asList(book1, book2);


        when(bookRepositoryMock.findAllById(bookIds)).thenReturn(existingBooks);

        // WHEN
        List<Book> result = bookService.getBooksByIds(bookIds);

        // THEN
        assertEquals(existingBooks, result);
        assertDoesNotThrow(() -> bookService.getBooksByIds(bookIds));

        List<Long> nonExistingBookIds = Arrays.asList(1L, 3L);

        when(bookRepositoryMock.findAllById(nonExistingBookIds))
                .thenReturn(Arrays.asList(
                        Book.builder()
                                .id(1L)
                                .title("A criada")
                                .author(Author.builder().build())
                                .publisher(Publisher.builder().build())
                                .genre(new ArrayList<>())
                                .translation(new ArrayList<>())
                                .edition(1)
                                .yearRelease(2022)
                                .price(9.99)
                                .rating(4.5)
                                .pageCount(200)
                                .review(new ArrayList<>()).build()));

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.getBooksByIds(nonExistingBookIds);
        });
        assertEquals("Book with the id/s: [3] doesn't exist/s.", exception.getMessage());
    }

    @Test
    @DisplayName("Get book by title and check exception")
    void testGetBookByTitle() {

        // GIVEN
        String bookTitle = "A criada";
        Book book = new Book();
        book.setTitle(bookTitle);

        when(bookRepositoryMock.findByTitle(bookTitle)).thenReturn(Optional.of(book));
        when(bookRepositoryMock.findByTitle("A criada")).thenReturn(Optional.empty());

    }

    @Test
    @DisplayName("Get Books by year release and check exception")
    void testGetBooksByYearRelease() throws IncorrectReleaseYearException {

        // GIVEN
        int releaseYear = 2022;
        Book book1 = Book.builder()
                .id(1L)
                .title("A criada")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .genre(new ArrayList<>())
                .translation(new ArrayList<>())
                .edition(1)
                .yearRelease(2022)
                .price(9.99)
                .rating(4.5)
                .pageCount(200)
                .review(new ArrayList<>()).build();

        Book book2 = Book.builder()
                .id(2L)
                .title("A familia perfeita")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .genre(new ArrayList<>())
                .translation(new ArrayList<>())
                .edition(2)
                .yearRelease(2022)
                .price(10.00)
                .rating(4.0)
                .pageCount(350)
                .review(new ArrayList<>()).build();

        List<Book> BookList = Arrays.asList(book1, book2);

        when(bookRepositoryMock.findBooksByYearRelease(releaseYear)).thenReturn(BookList);

        // WHEN
        List<BookYearReleaseInfoDto> result = bookService.getBooksByYearRelease(releaseYear);

        // THEN
        assertEquals(2, result.size());
        assertDoesNotThrow(() -> bookService.getBooksByYearRelease(releaseYear));

        int nonExistingReleaseYear = 2023;
        when(bookRepositoryMock.findBooksByYearRelease(nonExistingReleaseYear)).thenReturn(Collections.emptyList());

        IncorrectReleaseYearException exception = assertThrows(IncorrectReleaseYearException.class, () -> {
            bookService.getBooksByYearRelease(nonExistingReleaseYear);
        });
        assertEquals("No books with release year: " + nonExistingReleaseYear, exception.getMessage());
    }

    @Test
    @DisplayName("Get Books by translation and check exception")
    void testGetBooksByTranslation() throws TranslationNotFoundException {

        // GIVEN
        Long translationId = 1L;
        Translation translation = new Translation();
        translation.setId(translationId);

        Book book1 = Book.builder()
                .id(1L)
                .title("A criada")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .genre(new ArrayList<>())
                .price(9.99)
                .build();

        Book book2 = Book.builder()
                .id(2L)
                .title("A familia perfeita")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .price(10.00)
                .review(new ArrayList<>())
                .build();

        List<Book> bookList = Arrays.asList(book1, book2);

        when(translationRepositoryMock.findById(translationId)).thenReturn(Optional.of(translation));
        when(bookRepositoryMock.findBooksByTranslation(translationId)).thenReturn(bookList);

        // WHEN
        List<BookGetByTranslationDto> result = bookService.getBooksByTranslation(translationId);

        // THEN
        assertEquals(2, result.size());

        assertDoesNotThrow(() -> bookService.getBooksByTranslation(translationId));

        Long nonExistingTranslationId = 2L;
        when(translationRepositoryMock.findById(nonExistingTranslationId)).thenReturn(Optional.empty());

        TranslationNotFoundException exception = assertThrows(TranslationNotFoundException.class, () -> {
            bookService.getBooksByTranslation(nonExistingTranslationId);
        });
        String expected = "Translation with id:\\s*2\\s*doesn't exist";
        String actual = "Translation with id: 2 doesn't exist";

        Assertions.assertTrue(
                actual.matches(expected),
                "Expected message not found in actual message"
        );

        when(translationRepositoryMock.findById(translationId)).thenReturn(Optional.of(translation));
        when(bookRepositoryMock.findBooksByTranslation(translationId)).thenReturn(Collections.emptyList());

        TranslationNotFoundException exception2 = assertThrows(TranslationNotFoundException.class, () -> {
            bookService.getBooksByTranslation(translationId);
        });
        assertEquals("No books with translation: " + translationId, exception2.getMessage());
    }
}
