package mindera.backendProject.bookStore.ControllersTests.BookControllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import mindera.backendProject.bookStore.model.Translation;
import mindera.backendProject.bookStore.repository.bookRepository.TranslationRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class TranslationControllerTest {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TranslationRepository translationRepository;

    @BeforeEach
    void cleanDB() {
        translationRepository.deleteAll();
        translationRepository.resetAutoIncrement();
    }

    @Test
    @DisplayName("Test get all translations when 0 translations  on database returns list with 0 translations")
    void testGetAllTranslationsWhenListEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/translations/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Test get all translations when 2 translations on database returns list with 2 translations")
    void testGetAllAuthorsWhen2AuthorsOnDatabase() throws Exception {

        Translation translation1 = Translation.builder().name("Translation1").build();
        Translation translation2 = Translation.builder().name("Translation2").build();

        translationRepository.save(translation1);
        translationRepository.save(translation2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/translations/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }


}
