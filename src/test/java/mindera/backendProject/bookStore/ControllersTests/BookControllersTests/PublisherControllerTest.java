package mindera.backendProject.bookStore.ControllersTests.BookControllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import mindera.backendProject.bookStore.dto.book.PublisherCreateDto;
import mindera.backendProject.bookStore.model.Publisher;
import mindera.backendProject.bookStore.repository.bookRepository.PublisherRepository;
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


    @Test
    @DisplayName("Test get publisher by id")
    void testGetPublisherById() throws Exception {

        Publisher publisher = Publisher.builder().name("Editora").build();

        publisherRepository.save(publisher);


        Long publisherId = publisher.getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/publishers/id/{publisherId}", publisherId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Editora"));

    }

    @Test
    @DisplayName("Test create a publisher and returns a status code 201")
    void testCreatePublisherAndReturnsStatus201() throws Exception {

        PublisherCreateDto publisherCreateDto = new PublisherCreateDto("Editora");
        String jsonRequest = objectMapper.writeValueAsString(publisherCreateDto);

        mockMvc.perform(post("/api/v1/publishers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonRequest));

        List<Publisher> publishers = publisherRepository.findAll();
        assertThat(publishers).hasSize(1);
        assertThat(publishers.getFirst().getName()).isEqualTo("Editora");

    }

    @Test
    @DisplayName("Test create a publisher with empty name")
    void testCreatePublisherWithEmptyName() throws Exception {
        String jsonRequest = "{\"name\": \"\"}";

        mockMvc.perform(post("/api/v1/publishers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test create a publisher that already exists in DB")
    void testCreatePublisherAlreadyExists() throws Exception {

        Publisher publisherExists = Publisher.builder().name("Editora").build();
        publisherRepository.save(publisherExists);

        PublisherCreateDto publisherCreateDtoExists = new PublisherCreateDto("Editora");


        String jsonRequest = objectMapper.writeValueAsString(publisherCreateDtoExists);
        mockMvc.perform(post("/api/v1/publishers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().string(Messages.PUBLISHER_ALREADY_EXISTS));
    }

}
