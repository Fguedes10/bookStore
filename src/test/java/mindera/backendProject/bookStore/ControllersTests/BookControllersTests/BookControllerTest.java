package mindera.backendProject.bookStore.ControllersTests.BookControllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import mindera.backendProject.bookStore.repository.bookRepository.BookRepository;
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
public class BookControllerTest {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    void cleanDB() {
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("Test get all books when 0 books on database returns list with 0 books")
    void testGetAllBooksWhenListEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

   /* @Test
    @DisplayName("Test get Book by id")
    void testGetBookById() throws Exception {

        Book book = Book.builder().title("A criada").build();

        bookRepository.save(book);

        Long bookId = book.getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/id/{bookId}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("A criada"));

    }
*/
 /*   @Test
    @DisplayName("Test create a book and returns a status code 201")
    void testCreateBookAndReturnsStatus201() throws Exception {

        BookCreateDto bookCreateDto = new BookCreateDto(
                "A criada",
                478521L,
                1L,
                1L,
                List.of(1L),
                List.of(1L),
                1,
                2022,
                5.99);
        String jsonRequest = objectMapper.writeValueAsString(bookCreateDto);

        mockMvc.perform(post("/api/v1/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonRequest));

        assertThat(bookCreateDto.title()).isEqualTo("A criada");
        assertThat(bookCreateDto.isbn()).isEqualTo(478521L);
        assertThat(bookCreateDto.authorId()).isEqualTo(1L);
        assertThat(bookCreateDto.publisherId()).isEqualTo(1L);
        assertThat(bookCreateDto.genre().stream().
        assertThat(bookCreateDto.translation().
        assertThat(bookCreateDto.edition()).isEqualTo(1);
        assertThat(bookCreateDto.yearRelease()).isEqualTo(2022);
        assertThat(bookCreateDto.price()).isEqualTo(5.99);


    }*/

}
