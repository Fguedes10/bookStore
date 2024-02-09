package mindera.backendProject.bookStore.ControllersTests.BookControllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import mindera.backendProject.bookStore.repository.bookRepository.ReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @AfterEach
    void cleanDB() {
        reviewRepository.deleteAll();
        reviewRepository.resetAutoIncrement();
    }


}
