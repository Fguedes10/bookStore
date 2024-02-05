package mindera.backendProject.bookStore.ServiceTests.BookServiceTests;

import mindera.backendProject.bookStore.converter.book.GenreConverter;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.exception.book.AuthorNotFoundException;
import mindera.backendProject.bookStore.exception.book.GenreAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.GenreNotFoundException;
import mindera.backendProject.bookStore.model.Genre;
import mindera.backendProject.bookStore.repository.bookRepository.GenreRepository;
import mindera.backendProject.bookStore.service.bookService.GenreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GenreServiceTest {

    static MockedStatic<GenreConverter> mockedGenreConverter = Mockito.mockStatic(GenreConverter.class);
    @MockBean
    private GenreRepository genreRepositoryMock;
    @InjectMocks
    private GenreServiceImpl genreService;

    @BeforeEach
    public void setUp() {

        genreService = new GenreServiceImpl(genreRepositoryMock);
    }


    @Test
    void testGetAll() {
        Genre genre1 = Genre.builder().id(1L).name("Action").build();
        Genre genre2 = Genre.builder().id(2L).name("Drama").build();

        List<Genre> genreList = List.of(genre1, genre2);

        when(genreRepositoryMock.findAll()).thenReturn(genreList);
        mockedGenreConverter.when(() -> GenreConverter.fromModelToGenreCreateDto(genre1))
                .thenReturn(new GenreCreateDto(genre1.getName()));
        mockedGenreConverter.when(() -> GenreConverter.fromModelToGenreCreateDto(genre2))
                .thenReturn(new GenreCreateDto(genre2.getName()));

        List<GenreCreateDto> resultDtos = genreService.getAll();

        assertNotNull(resultDtos);
        assertEquals(2, resultDtos.size());

        verify(genreRepositoryMock).findAll();

        mockedGenreConverter.verify(() -> GenreConverter.fromModelToGenreCreateDto(genre1));
        mockedGenreConverter.verify(() -> GenreConverter.fromModelToGenreCreateDto(genre2));

    }

    @Test
    void testGetGenre() throws GenreNotFoundException {
        Long genreId = 1L;

        Genre genre = new Genre();
        genre.setId(genreId);
        genre.setName("Drama");

        when(genreRepositoryMock.findById(genreId)).thenReturn(Optional.of(genre));

        GenreCreateDto expectedDto = new GenreCreateDto("Drama");

        when(GenreConverter.fromModelToGenreCreateDto(genre)).thenReturn(new GenreCreateDto("Drama"));

        GenreCreateDto resultDto = genreService.getGenre(genreId);

        assertEquals(expectedDto, resultDto);
        verify(genreRepositoryMock, times(1)).findById(genreId);

        mockedGenreConverter.verify(() -> GenreConverter.fromModelToGenreCreateDto(genre));
    }

    @Test
    void testGetGenreThrowsException() {

        Long genreId = 1L;

        when(genreRepositoryMock.findById(genreId)).thenReturn(Optional.empty());

        assertThrows(GenreNotFoundException.class, () -> genreService.getGenre(genreId));

        verify(genreRepositoryMock, times(1)).findById(genreId);
    }

    @Test
    void testAddGenreAlreadyExists() {
        // Arrange
        GenreCreateDto existingGenreDto = new GenreCreateDto("Genre exists already");

        when(genreRepositoryMock.findByName("Genre exists already")).thenReturn(Optional.of(new Genre()));

        assertThrows(GenreAlreadyExistsException.class, () -> genreService.add(existingGenreDto));

        verify(genreRepositoryMock, times(1)).findByName("Genre exists already");

        verify(genreRepositoryMock, never()).save(any(Genre.class));
    }


    @Test
    void testGetGenreByName() throws AuthorNotFoundException, GenreNotFoundException {
        String genreNAme = "Drama";
        Genre testGenre = new Genre();
        testGenre.setName(genreNAme);

        when(genreRepositoryMock.findByName(genreNAme)).thenReturn(Optional.of(testGenre));
        when(genreRepositoryMock.findByName("Non Existing Genre")).thenReturn(Optional.empty());

        mockedGenreConverter.when(() -> GenreConverter.fromModelToGenreCreateDto(testGenre))
                .thenReturn(new GenreCreateDto(genreNAme));

        GenreCreateDto resultDto = genreService.getGenreByName(genreNAme);
        assertNotNull(resultDto);

        verify(genreRepositoryMock).findByName(genreNAme);
        mockedGenreConverter.verify(() -> GenreConverter.fromModelToGenreCreateDto(testGenre));

        assertThrows(GenreNotFoundException.class, () -> genreService.getGenreByName("Non Existing Genre"));
    }

    @Test
    void testFindByIds() throws GenreNotFoundException {

        List<Long> genreIds = List.of(1L, 2L);
        Genre genre1 = Genre.builder().id(1L).name("Action").build();
        Genre genre2 = Genre.builder().id(2L).name("Drama").build();

        when(genreRepositoryMock.findAllByIdIn(genreIds)).thenReturn(List.of(genre1, genre2));

        List<Genre> resultGenres = genreService.findByIds(genreIds);

        assertNotNull(resultGenres);
        assertEquals(2, resultGenres.size());

        verify(genreRepositoryMock).findAllByIdIn(genreIds);


        assertThrows(GenreNotFoundException.class, () -> genreService.findByIds(List.of(1L, 4L, 5L)));
    }


}
