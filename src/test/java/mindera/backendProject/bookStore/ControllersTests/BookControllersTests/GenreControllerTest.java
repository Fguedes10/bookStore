package mindera.backendProject.bookStore.ControllersTests.BookControllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.model.Genre;
import mindera.backendProject.bookStore.repository.bookRepository.GenreRepository;
import mindera.backendProject.bookStore.util.Messages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerTest {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GenreRepository genreRepository;

    @AfterEach
    void cleanDB() {
        genreRepository.deleteAll();
        genreRepository.resetAutoIncrement();
    }

    @Test
    @DisplayName("Test get all Genres when 0 genres on database returns list with 0 genres")
    void testGetAllGenresWhenListEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/genres/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Test get all genres when 2 genres on database returns list with 2 genres")
    void testGetAllGenresWhen2GenresOnDatabase() throws Exception {

        Genre genre1 = Genre.builder().name("Genre1").build();
        Genre genre2 = Genre.builder().name("Genre2").build();

        genreRepository.save(genre1);
        genreRepository.save(genre2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/genres/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Test get Genre by id")
    void testGetGenreById() throws Exception {

        Genre genre = Genre.builder().name("Drama").build();

        genreRepository.save(genre);

        Long genreId = genre.getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/genres/id/{genreId}", genreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Drama"));

    }

    @Test
    @DisplayName("Test create a genre and returns a status code 201")
    void testCreateGenreAndReturnsStatus201() throws Exception {

        GenreCreateDto genreCreateDto = new GenreCreateDto("Drama");
        String jsonRequest = objectMapper.writeValueAsString(genreCreateDto);

        mockMvc.perform(post("/api/v1/genres/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonRequest));

        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).hasSize(1);
        assertThat(genres.getFirst().getName()).isEqualTo("Drama");

    }

    @Test
    @DisplayName("Test create a genre with empty name")
    void testCreateGenreWithEmptyName() throws Exception {
        String jsonRequest = "{\"name\": \"\"}";

        mockMvc.perform(post("/api/v1/genres/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test create a genre that already exists in DB")
    void testCreateGenreAlreadyExists() throws Exception {

        Genre genreExists = Genre.builder().name("Drama").build();
        genreRepository.save(genreExists);

        GenreCreateDto genreCreateDtoExists = new GenreCreateDto("Drama");


        String jsonRequest = objectMapper.writeValueAsString(genreCreateDtoExists);
        mockMvc.perform(post("/api/v1/genres/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().string(Messages.GENRE_ALREADY_EXISTS));
    }
}
