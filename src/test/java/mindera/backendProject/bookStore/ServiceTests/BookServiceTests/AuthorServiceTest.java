package mindera.backendProject.bookStore.ServiceTests.BookServiceTests;

import mindera.backendProject.bookStore.converter.book.AuthorConverter;
import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;
import mindera.backendProject.bookStore.exception.book.AuthorAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.AuthorNotFoundException;
import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.repository.bookRepository.AuthorRepository;
import mindera.backendProject.bookStore.service.bookService.AuthorServiceImpl;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AuthorServiceTest {


    static MockedStatic<AuthorConverter> mockedAuthorConverter = Mockito.mockStatic(AuthorConverter.class);
    @MockBean
    private AuthorRepository authorRepositoryMock;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    public void setUp() {
        authorService = new AuthorServiceImpl(authorRepositoryMock);
    }


    @Test
    @DisplayName("Get all authors and check repository")
    void testGetAll() {
        // GIVEN

        int page = 0;
        int size = 10;
        String searchTerm = "firstName";

        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("João Silva");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Ana Almeida");

        List<Author> mockAuthorList = Arrays.asList(author1, author2);
        Page<Author> mockedPage = new PageImpl<>(mockAuthorList);


        when(authorRepositoryMock.findAll(any(PageRequest.class))).thenReturn(mockedPage);

        AuthorCreateDto dto1 = new AuthorCreateDto("João Silva");
        AuthorCreateDto dto2 = new AuthorCreateDto("Ana Almeida");

        when(AuthorConverter.fromModelToAuthorCreateDto(author1)).thenReturn(dto1);
        when(AuthorConverter.fromModelToAuthorCreateDto(author2)).thenReturn(dto2);

        // WHEN
        List<AuthorCreateDto> result = authorService.getAll(page, size, searchTerm);

        // THEN
        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));


        verify(authorRepositoryMock, times(1)).findAll(PageRequest.of(page, size, Sort.Direction.DESC, searchTerm));
        mockedAuthorConverter.verify(() -> AuthorConverter.fromModelToAuthorCreateDto(author1));
        mockedAuthorConverter.verify(() -> AuthorConverter.fromModelToAuthorCreateDto(author2));


    }

    @Test
    @DisplayName("Get author by id and check repository")
    void testGetAuthor() throws AuthorNotFoundException {
        // GIVEN
        Long authorId = 1L;

        Author author = new Author();
        author.setId(authorId);
        author.setName("João Silva");

        // WHEN
        when(authorRepositoryMock.findById(authorId)).thenReturn(Optional.of(author));

        AuthorCreateDto expectedDto = new AuthorCreateDto("João Silva");

        when(AuthorConverter.fromModelToAuthorCreateDto(author)).thenReturn(new AuthorCreateDto("João Silva"));

        AuthorCreateDto resultDto = authorService.getAuthor(authorId);

        // THEN
        assertEquals(expectedDto, resultDto);
        verify(authorRepositoryMock, times(1)).findById(authorId);

        mockedAuthorConverter.verify(() -> AuthorConverter.fromModelToAuthorCreateDto(author));
    }

    @Test
    @DisplayName("Get author when the requested author does not exist in the DB")
    void testGetAuthorThrowsException() {

        // GIVEN
        Long authorId = 1L;

        // WHEN
        when(authorRepositoryMock.findById(authorId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthor(authorId));

        verify(authorRepositoryMock, times(1)).findById(authorId);
    }

    @Test
    @DisplayName("Add author with duplicate name and check exception")
    void testAddAuthorAlreadyExists() {
        // GIVEN
        AuthorCreateDto existingAuthorDto = new AuthorCreateDto("Author exists already");

        // WHEN
        when(authorRepositoryMock.findByName("Author exists already")).thenReturn(Optional.of(new Author()));

        // THEN
        assertThrows(AuthorAlreadyExistsException.class, () -> authorService.add(existingAuthorDto));

        verify(authorRepositoryMock, times(1)).findByName("Author exists already");

        verify(authorRepositoryMock, never()).save(any(Author.class));
    }


    @Test
    @DisplayName("Add an author and check repository and check exception")
    void testAddAuthorSuccessfully() {

        // GIVEN
        AuthorCreateDto authorExistingDto = new AuthorCreateDto("Existing Author");

        when(authorRepositoryMock.findByName(authorExistingDto.name())).thenReturn(Optional.of(new Author()));

        // WHEN / THEN
        assertThrows(AuthorAlreadyExistsException.class, () -> {
            authorService.add(authorExistingDto);
        });
        verify(authorRepositoryMock, never()).save(any(Author.class));

    }

    @Test
    @DisplayName("Get author by name and check exception")
    void testGetAuthorByName() throws AuthorNotFoundException {
        // GIven
        String authorName = "João Silva";
        Author testAuthor = new Author();
        testAuthor.setName(authorName);


        when(authorRepositoryMock.findByName(authorName)).thenReturn(Optional.of(testAuthor));
        when(authorRepositoryMock.findByName("Non Existing Author")).thenReturn(Optional.empty());


        mockedAuthorConverter.when(() -> AuthorConverter.fromModelToAuthorCreateDto(testAuthor))
                .thenReturn(new AuthorCreateDto(authorName));

        // WHEN
        AuthorCreateDto resultDto = authorService.getAuthorByName(authorName);

        // THEN
        assertNotNull(resultDto);

        verify(authorRepositoryMock).findByName(authorName);
        mockedAuthorConverter.verify(() -> AuthorConverter.fromModelToAuthorCreateDto(testAuthor));

        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorByName("Non Existing Author"));
    }

    @Test
    @DisplayName("Test findByIds - Existing Authors")
    void testGetAuthorById() throws AuthorNotFoundException {

        // GIVEN
        Long authorId = 1L;
        Author testAuthor = new Author();
        testAuthor.setId(authorId);

        // WHEN
        when(authorRepositoryMock.findById(authorId)).thenReturn(Optional.of(testAuthor));
        when(authorRepositoryMock.findById(55L)).thenReturn(Optional.empty());

        Author resultAuthor = authorService.getAuthorById(authorId);

        assertNotNull(resultAuthor);
        assertEquals(authorId, resultAuthor.getId());

        // THEN
        verify(authorRepositoryMock).findById(authorId);

        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorById(55L));
    }
}
