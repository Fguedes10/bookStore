package mindera.backendProject.bookStore.ServiceTests.CustomerServiceTests;

import mindera.backendProject.bookStore.converter.customer.CustomerConverter;
import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerGetDto;
import mindera.backendProject.bookStore.exception.customer.CustomerAlreadyExistsException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.model.*;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import mindera.backendProject.bookStore.service.bookService.BookServiceImpl;
import mindera.backendProject.bookStore.service.bookService.GenreServiceImpl;
import mindera.backendProject.bookStore.service.customerService.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    static MockedStatic<CustomerConverter> mockedCustomerConverter = Mockito.mockStatic(CustomerConverter.class);
    @MockBean
    private CustomerRepository customerRepositoryMock;
    @MockBean
    private GenreServiceImpl genreService;
    @MockBean
    private BookServiceImpl bookService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        customerService = new CustomerServiceImpl(customerRepositoryMock, genreService, bookService);
    }


    @Test
    @DisplayName("Get all customers and check repository")
    void testGetCustomers() {

        // Given

        int page = 0;
        int size = 10;
        String searchTerm = "firstName";

        Customer customer1 = Customer.builder()
                .id(1L)
                .firstName("John")
                .lastName("Daves")
                .nif(4523652L)
                .email("john@mindera.pt")
                .username("JohnDaves")
                .password("mypass").build();

        Customer customer2 = Customer.builder()
                .id(2L)
                .firstName("Mary")
                .lastName("Rose")
                .nif(4523651L)
                .email("mary@mindera.pt")
                .username("MarieRose")
                .password("pass").build();

        List<Customer> customers = List.of(customer1, customer2);
        Page<Customer> mockedPage = new PageImpl<>(customers);

        when(customerRepositoryMock.findAll(any(PageRequest.class))).thenReturn(mockedPage);
        mockedCustomerConverter.when(() -> CustomerConverter.fromEntityToCustomerGetDto(customer1)).thenReturn(new CustomerGetDto(
                customer1.getFirstName(),
                customer1.getLastName(),
                customer1.getEmail(),
                new ArrayList<>()));

        mockedCustomerConverter.when(() -> CustomerConverter.fromEntityToCustomerGetDto(customer2)).thenReturn(new CustomerGetDto(
                customer2.getFirstName(),
                customer2.getLastName(),
                customer2.getEmail(),
                new ArrayList<>()));


        // WHEN
        List<CustomerGetDto> result = customerService.getCustomers(page, size, searchTerm);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(customerRepositoryMock).findAll(PageRequest.of(page, size, Sort.Direction.DESC, searchTerm));
        mockedCustomerConverter.verify(() -> CustomerConverter.fromEntityToCustomerGetDto(customer1));
        mockedCustomerConverter.verify(() -> CustomerConverter.fromEntityToCustomerGetDto(customer2));
    }

    @Test
    @DisplayName("Get customer by Id and check repository")
    void testGetCustomer() throws CustomerNotFoundException {

        //GIVEN
        Long customerId = 1L;
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("John")
                .lastName("Daves")
                .email("john@mindera.pt")
                .favoriteGenres(new ArrayList<>())
                .build();

        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(customer));
        CustomerGetDto expectedDto = new CustomerGetDto(
                "John",
                "Daves",
                "john@mindera.pt",
                new ArrayList<>());

        when(CustomerConverter.fromEntityToCustomerGetDto(customer)).thenReturn(new CustomerGetDto(
                "John",
                "Daves",
                "john@mindera.pt",
                new ArrayList<>()));

        // WHEN
        CustomerGetDto result = customerService.getCustomer(customerId);

        // THEN
        assertEquals(expectedDto, result);
        verify(customerRepositoryMock, times(1)).findById(customerId);
        mockedCustomerConverter.verify(() -> CustomerConverter.fromEntityToCustomerGetDto(customer));

    }

    @Test
    @DisplayName("Get customer by username and check exception")
    void testGetCustomerByUsername() throws CustomerNotFoundException {

        //GIVEN
        String customerUsername = "johnDaves";
        Customer customer = new Customer();
        customer.setUsername(customerUsername);

        when(customerRepositoryMock.findByUsername(customerUsername)).thenReturn(Optional.of(customer));
        when(customerRepositoryMock.findByUsername("Customer doesn´t exists")).thenReturn(Optional.empty());

        mockedCustomerConverter.when(() -> CustomerConverter.fromEntityToCustomerGetDto(customer)).thenReturn(new CustomerGetDto(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                new ArrayList<>()));

        // WHEN
        CustomerGetDto result = customerService.getCustomerByUsername(customerUsername);

        // THEN
        assertNotNull(result);
        verify(customerRepositoryMock).findByUsername(customerUsername);
        mockedCustomerConverter.verify(() -> CustomerConverter.fromEntityToCustomerGetDto(customer));
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerByUsername("Costumer doesn´t exists"));
    }

    @Test
    @DisplayName("Add a customer and check repository")
    void testCreateCustomerSuccessfully() {

        // GIVEN
        CustomerCreateDto customerExistingDto = new CustomerCreateDto("johnDaves", "John", "Daves", "john@mindera.pt", 125452L, new ArrayList<>());

        when(customerRepositoryMock.findByUsername(customerExistingDto.username())).thenReturn(Optional.of(new Customer()));
        when(customerRepositoryMock.findByNif(customerExistingDto.nif())).thenReturn(Optional.of(new Customer()));
        when(customerRepositoryMock.findByEmail(customerExistingDto.email())).thenReturn(Optional.of(new Customer()));

        // WHEN / THEN
        assertThrows(CustomerAlreadyExistsException.class, () -> {
            customerService.createCustomer(customerExistingDto);
        });
        verify(customerRepositoryMock, never()).save(any(Customer.class));

    }


    @Test
    @DisplayName("Add customer with duplicate nif and check exception")
    void testAddCustomerAlreadyExistsByNifCheckException() {

        //GIVEN
        CustomerCreateDto existingCustomerDto = new CustomerCreateDto(
                "johnDaves",
                "John",
                "Daves",
                "johndaves@mindera.pt",
                1254125L,
                new ArrayList<>());

        // WHEN
        when(customerRepositoryMock.findByNif(1254125L)).thenReturn(Optional.of(new Customer()));

        // THEN
        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.createCustomer(existingCustomerDto));
        verify(customerRepositoryMock, times(1)).findByNif(1254125L);
        verify(customerRepositoryMock, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Add customer with duplicate email and check exception")
    void testAddCustomerAlreadyExistsByEmailCheckException() {

        //GIVEN
        CustomerCreateDto existingCustomerDto = new CustomerCreateDto(
                "johnDaves",
                "John",
                "Daves",
                "johndaves@mindera.pt",
                1254125L,
                new ArrayList<>());

        // WHEN
        when(customerRepositoryMock.findByEmail("johndaves@mindera.pt")).thenReturn(Optional.of(new Customer()));

        // THEN
        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.createCustomer(existingCustomerDto));
        verify(customerRepositoryMock, times(1)).findByEmail("johndaves@mindera.pt");
        verify(customerRepositoryMock, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Add customer with duplicate username and check exception")
    void testAddCustomerAlreadyExistsByUsernameCheckException() {

        //GIVEN
        CustomerCreateDto existingCustomerDto = new CustomerCreateDto("johnDaves", "John", "Daves", "johndaves@mindera.pt", 1254125L, new ArrayList<>());

        // WHEN
        when(customerRepositoryMock.findByUsername("johnDaves")).thenReturn(Optional.of(new Customer()));

        // THEN
        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.createCustomer(existingCustomerDto));
        verify(customerRepositoryMock, times(1)).findByUsername("johnDaves");
        verify(customerRepositoryMock, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Verify if customer exists and check exception")
    void testVerifyIfCustomerExists() {

        // GIVEN
        CustomerCreateDto customerToCreate = new CustomerCreateDto(
                "johnDaves",
                "John",
                "Daves",
                "johndaves@mindera.pt",
                1254125L,
                new ArrayList<>());

        when(customerRepositoryMock.findByUsername("johnDaves")).thenReturn(Optional.of(new Customer()));
        when(customerRepositoryMock.findByEmail("johndaves@mindera.pt")).thenReturn(Optional.empty());
        when(customerRepositoryMock.findByNif(1254125L)).thenReturn(Optional.empty());

        // WHEN / THEN
        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.verifyIfCustomerExists(customerToCreate));
    }

    @Test
    @DisplayName("Get customer favorite Genres books by id and check exception")
    void testGetFavoriteGenresById() throws CustomerNotFoundException {

        // GIVEN
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);

        Genre genre1 = Genre.builder().id(1L).name("Action").build();
        Genre genre2 = Genre.builder().id(2L).name("Drama").build();
        List<Genre> favoriteGenres = List.of(genre1, genre2);

        customer.setFavoriteGenres(favoriteGenres);
        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(customer));

        // When
        List<GenreCreateDto> result = customerService.getFavoriteGenresById(customerId);

        // Then
        assertEquals(2, result.size());
        assertEquals("Action", result.get(0).name());
        assertEquals("Drama", result.get(1).name());
    }

    @Test
    @DisplayName("Test Exception Customer not found by Id")
    void testCustomerNotFoundException() {

        // GIVEN
        Long customerId = 1L;
        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.empty());

        // WHEN/THEN
        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(customerId));
    }

    @Test
    @DisplayName("Get customer favorite Books by id and check exception")
    void testGetFavoriteBooksById() throws CustomerNotFoundException {

        // GIVEN
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);

        Book book1 = Book.builder()
                .id(1L)
                .title("A criada")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .genre(new ArrayList<>())
                .translation(new ArrayList<>())
                .edition(1)
                .yearRelease(2022)
                .price(9.99)
                .rating(4.5)
                .pageCount(200)
                .review(new ArrayList<>()).build();


        Book book2 = Book.builder()
                .id(2L)
                .title("A familia perfeita")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .genre(new ArrayList<>())
                .translation(new ArrayList<>())
                .edition(2)
                .yearRelease(2023)
                .price(10.00)
                .rating(4.0)
                .pageCount(350)
                .review(new ArrayList<>()).build();

        List<Book> favoriteBooks = List.of(book1, book2);

        customer.setFavoriteBooks(favoriteBooks);
        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(customer));

        // WHEN
        List<BookGetDto> result = customerService.getFavoriteBooksById(customerId);

        // THEN
        assertEquals(2, result.size());
        assertEquals("A criada", result.get(0).title());
        assertEquals(1, result.get(0).edition());
        assertEquals(2022, result.get(0).yearRelease());
        assertEquals(9.99, result.get(0).price());
        assertEquals(4.5, result.get(0).rating());
        assertEquals(200, result.get(0).pageCount());

        assertEquals("A familia perfeita", result.get(1).title());
        assertEquals(2, result.get(1).edition());
        assertEquals(2023, result.get(1).yearRelease());
        assertEquals(10.00, result.get(1).price());
        assertEquals(4.0, result.get(1).rating());
        assertEquals(350, result.get(1).pageCount());
    }


    @Test
    @DisplayName("Test findById - Existing Customers")
    void testFindById() throws CustomerNotFoundException {

        // GIVEN
        Long customerId = 1L;
        Customer customer = Customer.builder().id(1L).firstName("John").lastName("Daves").email("john@mindera.pt").build();

        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(customer));

        // WHEN
        Customer result = customerService.findById(customerId);

        // THEN
        assertNotNull(result);
        assertEquals(customerId, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Daves", result.getLastName());
        assertEquals("john@mindera.pt", result.getEmail());
        verify(customerRepositoryMock).findById(customerId);
    }


}
