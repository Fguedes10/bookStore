package mindera.backendProject.bookStore.ServiceTests.BookServiceTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mindera.backendProject.bookStore.controller.book.AuthorController;
import mindera.backendProject.bookStore.repository.bookRepository.AuthorRepository;
import mindera.backendProject.bookStore.service.bookService.AuthorService;
import mindera.backendProject.bookStore.service.bookService.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthorServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthorServiceImpl authorServiceImpl;

    @Autowired
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorController authorController;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void init() {
        authorRepository.deleteAll();
        authorRepository.resetAutoIncrement();
    }

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Test get all authors when no authors on database returns empty list")
    void testGetAllAuthorsWhenNoAuthorsOnDatabaseReturnsEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/version1/authors/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

        verify(authorServiceImpl, times(1)).getAll();
    }
}
