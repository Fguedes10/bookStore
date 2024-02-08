package mindera.backendProject.bookStore.ControllersTests.BookControllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import mindera.backendProject.bookStore.model.Publisher;
import mindera.backendProject.bookStore.repository.bookRepository.PublisherRepository;
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
public class PublisherControllerTest {
    private final static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PublisherRepository publisherRepository;

    @AfterEach
    void cleanDB() {
        publisherRepository.deleteAll();
        publisherRepository.resetAutoIncrement();
    }

    @Test
    @DisplayName("Test get all publishers when 0 publishers on database returns list with 0 publishers")
    void testGetAllPublishersWhenListEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/publishers/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Test get all publishers when 2 publishers on database returns list with 2 publishers")
    void testGetAllPublishersWhen2PublishersOnDatabase() throws Exception {

        Publisher publisher1 = Publisher.builder().name("Publisher1").build();
        Publisher publisher2 = Publisher.builder().name("Publisher2").build();

        publisherRepository.save(publisher1);
        publisherRepository.save(publisher2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/publishers/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }


}
