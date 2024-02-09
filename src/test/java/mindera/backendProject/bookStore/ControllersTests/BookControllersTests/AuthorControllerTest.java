package mindera.backendProject.bookStore.ControllersTests.BookControllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;
import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.repository.bookRepository.AuthorRepository;
import mindera.backendProject.bookStore.service.bookService.AuthorServiceImpl;
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
public class AuthorControllerTest {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorServiceImpl authorService;

    @AfterEach
    void cleanDB() {
        authorRepository.deleteAll();
        authorRepository.resetAutoIncrement();
    }

    @Test
    @DisplayName("Test create an author and returns a status code 201")
    void testCreateAuthorAndReturnsStatus201() throws Exception {

        AuthorCreateDto authorCreateDto = new AuthorCreateDto("John");
        String jsonRequest = objectMapper.writeValueAsString(authorCreateDto);

        mockMvc.perform(post("/api/v1/authors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonRequest));

        List<Author> authors = authorRepository.findAll();
        assertThat(authors).hasSize(1);
        assertThat(authors.getFirst().getName()).isEqualTo("John");
    }


    @Test
    @DisplayName("Test get all authors when 0 authors on database returns list with 0 authors")
    void testGetAllAuthorsWhenListEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/authors/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Test get Author by id")
    void testGetAuthorById() throws Exception {

        Author author = Author.builder().name("Author1").build();
        authorRepository.save(author);

        Long authorId = author.getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/authors/id/{authorId}", authorId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Author1"));

    }


    @Test
    @DisplayName("Test get all authors when 2 authors on database returns list with 2 authors")
    void testGetAllAuthorsWhen2AuthorsOnDatabase() throws Exception {

        Author author1 = Author.builder().name("Author1").build();
        Author author2 = Author.builder().name("Author2").build();

        authorRepository.save(author1);
        authorRepository.save(author2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/authors/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Test create an author with empty name")
    void testCreateAuthorWithEmptyName() throws Exception {
        String jsonRequest = "{\"name\": \"\"}";

        mockMvc.perform(post("/api/v1/authors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test create an author that already exists in DB")
    void testCreateAuthorAlreadyExists() throws Exception {

        Author authorExists = Author.builder().name("John").build();
        authorRepository.save(authorExists);

        AuthorCreateDto authorCreateDtoExists = new AuthorCreateDto("John");


        String jsonRequest = objectMapper.writeValueAsString(authorCreateDtoExists);
        mockMvc.perform(post("/api/v1/authors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().string(Messages.AUTHOR_ALREADY_EXISTS));
    }
}
