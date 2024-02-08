package mindera.backendProject.bookStore.ControllersTests.CustomerControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTests {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void cleanDB() {
        customerRepository.deleteAll();
        customerRepository.resetAutoIncrement();
    }

    @Test
    @DisplayName("Test get all customers when no customers on database returns empty list")
    void testGetAllCustomersWhenNoCustomersOnDatabaseReturnsEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    @DisplayName("Test get all customers when 2 customers on database returns list with 2 customers")
    void testGetAllCustomersWhen2CustomersOnDatabase() throws Exception {

        Customer customer1 = new Customer();
        Customer customer2 = new Customer();

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(2)));
    }

}
