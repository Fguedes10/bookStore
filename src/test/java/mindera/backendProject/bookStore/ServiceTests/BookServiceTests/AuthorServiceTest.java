package mindera.backendProject.bookStore.ServiceTests.BookServiceTests;

import mindera.backendProject.bookStore.converter.book.AuthorConverter;
import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;
import mindera.backendProject.bookStore.exception.book.AuthorAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.AuthorNotFoundException;
import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.repository.bookRepository.AuthorRepository;
import mindera.backendProject.bookStore.service.bookService.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    void testGetAll() {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("João Silva");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Ana Almeida");

        List<Author> mockAuthorList = Arrays.asList(author1, author2);
        when(authorRepositoryMock.findAll()).thenReturn(mockAuthorList);

        AuthorCreateDto dto1 = new AuthorCreateDto("João Silva");
        AuthorCreateDto dto2 = new AuthorCreateDto("Ana Almeida");

        when(AuthorConverter.fromModelToAuthorCreateDto(author1)).thenReturn(dto1);
        when(AuthorConverter.fromModelToAuthorCreateDto(author2)).thenReturn(dto2);

        List<AuthorCreateDto> result = authorService.getAll();
        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));

        verify(authorRepositoryMock, times(1)).findAll();
        mockedAuthorConverter.verify(() -> AuthorConverter.fromModelToAuthorCreateDto(author1));
        mockedAuthorConverter.verify(() -> AuthorConverter.fromModelToAuthorCreateDto(author2));


    }

    @Test
    void testGetAuthor() throws AuthorNotFoundException {
        Long authorId = 1L;

        Author author = new Author();
        author.setId(authorId);
        author.setName("João Silva");

        when(authorRepositoryMock.findById(authorId)).thenReturn(Optional.of(author));

        AuthorCreateDto expectedDto = new AuthorCreateDto("João Silva");

        when(AuthorConverter.fromModelToAuthorCreateDto(author)).thenReturn(new AuthorCreateDto("João Silva"));

        AuthorCreateDto resultDto = authorService.getAuthor(authorId);

        assertEquals(expectedDto, resultDto);
        verify(authorRepositoryMock, times(1)).findById(authorId);

        mockedAuthorConverter.verify(() -> AuthorConverter.fromModelToAuthorCreateDto(author));
    }

    @Test
    void testGetAuthorThrowsException() {

        Long authorId = 1L;

        when(authorRepositoryMock.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthor(authorId));

        verify(authorRepositoryMock, times(1)).findById(authorId);
    }

    @Test
    void testAddAuthorAlreadyExists() {
        // Arrange
        AuthorCreateDto existingAuthorDto = new AuthorCreateDto("Author exists already");

        when(authorRepositoryMock.findByName("Author exists already")).thenReturn(Optional.of(new Author()));

        assertThrows(AuthorAlreadyExistsException.class, () -> authorService.add(existingAuthorDto));

        verify(authorRepositoryMock, times(1)).findByName("Author exists already");

        verify(authorRepositoryMock, never()).save(any(Author.class));
    }


   /* @Test
    void testAddAuthorSuccessfully() throws AuthorAlreadyExistsException {
        AuthorCreateDto authorCreateDto = new AuthorCreateDto("Miguel Afonso");

        when(authorRepositoryMock.findByName("Miguel Afonso")).thenReturn(Optional.empty());

        when(authorRepositoryMock.save(any(Author.class))).thenAnswer(invocation -> {
            Author savedAuthor = invocation.getArgument(0);
            savedAuthor.setId(1L);
            return savedAuthor;
        });

        AuthorCreateDto resultDto = authorService.add(authorCreateDto);

        assertEquals("Miguel Afonso", resultDto.name());

        verify(authorRepositoryMock, times(1)).findByName("Miguel Afonso");


        verify(authorRepositoryMock, times(1)).save(argThat(author -> {
            return author != null && "Miguel Afonso".equals(author.getName()) && author.getId() != null;
        }));

        when(authorRepositoryMock.findByName("Miguel Afonso")).thenReturn(Optional.empty());

        when(authorRepositoryMock.save(any(Author.class))).thenAnswer(invocation -> {
            Author savedAuthor = invocation.getArgument(0);
            savedAuthor.setId(1L);
            return savedAuthor;
        });
        assertNotNull(resultDto.name());
    }*/

    @Test
    void testGetAuthorByName() throws AuthorNotFoundException {
        String authorName = "João Silva";
        Author testAuthor = new Author();
        testAuthor.setName(authorName);

        when(authorRepositoryMock.findByName(authorName)).thenReturn(Optional.of(testAuthor));
        when(authorRepositoryMock.findByName("Non Existing Author")).thenReturn(Optional.empty());

        mockedAuthorConverter.when(() -> AuthorConverter.fromModelToAuthorCreateDto(testAuthor))
                .thenReturn(new AuthorCreateDto(authorName));

        AuthorCreateDto resultDto = authorService.getAuthorByName(authorName);
        assertNotNull(resultDto);

        verify(authorRepositoryMock).findByName(authorName);
        mockedAuthorConverter.verify(() -> AuthorConverter.fromModelToAuthorCreateDto(testAuthor));

        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorByName("Non Existing Author"));
    }

    @Test
    void testGetAuthorById() throws AuthorNotFoundException {

        Long authorId = 1L;
        Author testAuthor = new Author();
        testAuthor.setId(authorId);


        when(authorRepositoryMock.findById(authorId)).thenReturn(Optional.of(testAuthor));
        when(authorRepositoryMock.findById(55L)).thenReturn(Optional.empty());

        Author resultAuthor = authorService.getAuthorById(authorId);

        assertNotNull(resultAuthor);
        assertEquals(authorId, resultAuthor.getId());

        verify(authorRepositoryMock).findById(authorId);

        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorById(55L));
    }
}
