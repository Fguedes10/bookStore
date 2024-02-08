package mindera.backendProject.bookStore.ControllersTests.BookControllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.repository.bookRepository.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthorRepository authorRepository;

    @AfterEach
    void cleanDB() {
        authorRepository.deleteAll();
        authorRepository.resetAutoIncrement();
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


}
