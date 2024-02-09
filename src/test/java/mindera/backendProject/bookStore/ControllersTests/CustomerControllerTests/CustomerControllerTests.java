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

 /*   @Test
    @DisplayName("Test create a customer that already exists in DB")
    void testCreateCustomerAlreadyExists() throws Exception {

        Customer customerExists = Customer.builder()
                .username("JohnDales")
                .firstName("John")
                .lastName("Dales")
                .email("john@pt.pt")
                .nif(412563L)
                .favoriteGenres(new ArrayList<>())
                .build();

        customerRepository.save(customerExists);

        CustomerCreateDto customerCreateDtoExists = new CustomerCreateDto("JohnDales", "John", "Dales", "john@pt.pt", 412563L, new ArrayList<>());


        String jsonRequest = objectMapper.writeValueAsString(customerCreateDtoExists);
        mockMvc.perform(post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().string(Messages.CUSTOMER_ALREADY_EXISTS));
    }*/


    /*@Test
    @DisplayName("Test create a customer and returns a status code 201")
    void testCreateCustomerAndReturnsStatus201() throws Exception {

        CustomerCreateDto customerCreateDto = new CustomerCreateDto(
                "johnDaves",
                "John",
                "Daves",
                "johndaves@mindera.pt",
                1254125L,
                List.of(1L));
        String jsonRequest = objectMapper.writeValueAsString(customerCreateDto);

        mockMvc.perform(post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonRequest));

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(1);
        assertThat(customers.get(0).getFirstName()).isEqualTo("John");
        assertThat(customers.get(0).getLastName()).isEqualTo("Daves");
        assertThat(customers.get(0).getUsername()).isEqualTo("JohnDaves");
        assertThat(customers.get(0).getEmail()).isEqualTo("johndaves@mindera.pt");
        assertThat(customers.get(0).getNif()).isEqualTo(1254125L);
        assertThat(customers.get(0).getFavoriteBooks().contains(1L));

    }*/

  /*  @Test
    @DisplayName("Test update a customer that doesn't exist")
    void testUpdateCustomerThatDoesntExist() throws Exception {

        String jsonUpdateRequest = "{\"username\": \"JasonDales\"}";

        // When trying to update a grape Variety that does not exist
        mockMvc.perform(patch("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Messages.CUSTOMER_WITH_ID + Messages.DOESNT_EXIST));
    }*/
}
