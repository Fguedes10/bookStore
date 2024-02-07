package mindera.backendProject.bookStore.ServiceTests.BookServiceTests;

import mindera.backendProject.bookStore.converter.book.GenreConverter;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.exception.book.GenreAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.GenreNotFoundException;
import mindera.backendProject.bookStore.model.Genre;
import mindera.backendProject.bookStore.repository.bookRepository.GenreRepository;
import mindera.backendProject.bookStore.service.bookService.GenreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Get all genres and check repository")
    void testGetAll() {

        //GIVEN
        Genre genre1 = Genre.builder().id(1L).name("Action").build();
        Genre genre2 = Genre.builder().id(2L).name("Drama").build();

        List<Genre> genreList = List.of(genre1, genre2);

        when(genreRepositoryMock.findAll()).thenReturn(genreList);
        mockedGenreConverter.when(() -> GenreConverter.fromModelToGenreCreateDto(genre1))
                .thenReturn(new GenreCreateDto(genre1.getName()));
        mockedGenreConverter.when(() -> GenreConverter.fromModelToGenreCreateDto(genre2))
                .thenReturn(new GenreCreateDto(genre2.getName()));

        // WHEN
        List<GenreCreateDto> resultDtos = genreService.getAll();


        // THEN
        assertNotNull(resultDtos);
        assertEquals(2, resultDtos.size());


        verify(genreRepositoryMock).findAll();
        mockedGenreConverter.verify(() -> GenreConverter.fromModelToGenreCreateDto(genre1));
        mockedGenreConverter.verify(() -> GenreConverter.fromModelToGenreCreateDto(genre2));

    }

    @Test
    @DisplayName("Get genre by id and check repository")
    void testGetGenre() throws GenreNotFoundException {

        // GIVEN
        Long genreId = 1L;

        Genre genre = new Genre();
        genre.setId(genreId);
        genre.setName("Drama");

        when(genreRepositoryMock.findById(genreId)).thenReturn(Optional.of(genre));
        GenreCreateDto expectedDto = new GenreCreateDto("Drama");
        when(GenreConverter.fromModelToGenreCreateDto(genre)).thenReturn(new GenreCreateDto("Drama"));

        // WHEN
        GenreCreateDto resultDto = genreService.getGenre(genreId);

        // THEN
        assertEquals(expectedDto, resultDto);
        verify(genreRepositoryMock, times(1)).findById(genreId);
        mockedGenreConverter.verify(() -> GenreConverter.fromModelToGenreCreateDto(genre));
    }

    @Test
    @DisplayName("Add a genre and check repository and Exception")
    void testAddGenreSuccessfully() {

        // GIVEN
        GenreCreateDto genreExistingDto = new GenreCreateDto("Existing Genre");

        when(genreRepositoryMock.findByName(genreExistingDto.name())).thenReturn(Optional.of(new Genre()));

        // WHEN / THEN
        assertThrows(GenreAlreadyExistsException.class, () -> {
            genreService.add(genreExistingDto);
        });
        verify(genreRepositoryMock, never()).save(any(Genre.class));

    }


    @Test
    @DisplayName("Get genre when the requested genre does not exist in the DB")
    void testGetGenreThrowsException() {

        // GIVEN
        Long genreId = 1L;

        // WHEN
        when(genreRepositoryMock.findById(genreId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(GenreNotFoundException.class, () -> genreService.getGenre(genreId));
        verify(genreRepositoryMock, times(1)).findById(genreId);
    }


    @Test
    @DisplayName("Add genre with duplicate name and check exception")
    void testAddGenreAlreadyExistsCheckException() {

        // GIVEN
        GenreCreateDto existingGenreDto = new GenreCreateDto("Drama");

        // WHEN
        when(genreRepositoryMock.findByName("Drama")).thenReturn(Optional.of(new Genre()));

        // THEN
        assertThrows(GenreAlreadyExistsException.class, () -> genreService.add(existingGenreDto));
        verify(genreRepositoryMock, times(1)).findByName("Drama");
        verify(genreRepositoryMock, never()).save(any(Genre.class));
    }


    @Test
    @DisplayName("Get genre by name and check exception")
    void testGetGenreByName() throws GenreNotFoundException {

        // GIVEN
        String genreName = "Drama";
        Genre testGenre = new Genre();
        testGenre.setName(genreName);

        when(genreRepositoryMock.findByName(genreName)).thenReturn(Optional.of(testGenre));
        when(genreRepositoryMock.findByName("Non Existing Genre")).thenReturn(Optional.empty());

        mockedGenreConverter.when(() -> GenreConverter.fromModelToGenreCreateDto(testGenre))
                .thenReturn(new GenreCreateDto(genreName));

        // WHEN
        GenreCreateDto resultDto = genreService.getGenreByName(genreName);

        // THEN
        assertNotNull(resultDto);
        verify(genreRepositoryMock).findByName(genreName);
        mockedGenreConverter.verify(() -> GenreConverter.fromModelToGenreCreateDto(testGenre));
        assertThrows(GenreNotFoundException.class, () -> genreService.getGenreByName("Non Existing Genre"));
    }

    @Test
    @DisplayName("Test findByIds - Existing Genres")
    void testFindByIds() throws GenreNotFoundException {

        // GIVEN
        List<Long> genreIds = List.of(1L, 2L);
        Genre genre1 = Genre.builder().id(1L).name("Action").build();
        Genre genre2 = Genre.builder().id(2L).name("Drama").build();

        when(genreRepositoryMock.findAllByIdIn(genreIds)).thenReturn(List.of(genre1, genre2));

        // WHEN
        List<Genre> resultGenres = genreService.findByIds(genreIds);

        // THEN
        assertNotNull(resultGenres);
        assertEquals(2, resultGenres.size());
        verify(genreRepositoryMock).findAllByIdIn(genreIds);
        assertThrows(GenreNotFoundException.class, () -> genreService.findByIds(List.of(1L, 4L, 5L)));
    }


}
