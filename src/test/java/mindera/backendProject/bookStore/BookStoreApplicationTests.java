package mindera.backendProject.bookStore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookStoreApplicationTests {

    private static ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void init() {
        customerRepository.deleteAll();
        customerRepository.resetAutoIncrement();
    }


    @Test
    @DisplayName("Test get all customers when no customers on database returns empty list")
    void testGetAllCustomersWhenNoCustomersOnDatabaseReturnsEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/version1/customers/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    @DisplayName("Test create customer when client returns code 201")
    void testCreateCustomerWhenClientReturnsDtoFields() throws Exception {

        String customerInputJson = "{\"username\": \"fguedes10\", \"firstName\": \"Fabio\", \"lastName\": \"Guedes\"," +
                "  " +
                "\"email\": \"email@email.com\",  \"nif\": \"123456789\",  \"favoriteGenre\": \"Drama\" }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/version1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerInputJson))
                .andExpect(status().isCreated())
                .andReturn();

    }


}
