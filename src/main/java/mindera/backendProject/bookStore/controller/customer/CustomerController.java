package mindera.backendProject.bookStore.controller.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.dto.customer.CustomerFavoriteDto;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerGetDto;
import mindera.backendProject.bookStore.dto.customer.CustomerPatchDto;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.book.GenreNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerAlreadyExistsException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerRepeatedFavoriteBooks;
import mindera.backendProject.bookStore.exception.customer.CustomerWithEmailAlreadyExists;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.service.customerService.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;

@Tag(name = CUSTOMER_TAG_NAME, description = CUSTOMER_TAG_DESCRIPTION)
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {


    @Autowired
    private CustomerServiceImpl customerService;


    @Operation(
            summary = GET_ALL_EXIST_CUSTOMERS,
            description = GET_ALL_EXIST_CUSTOMERS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = CUSTOMERS_FOUND)})
    @GetMapping("/search")
    public ResponseEntity<List<CustomerGetDto>> getCustomers(@RequestParam (defaultValue = "0", required = false) int page,
                                                             @RequestParam (defaultValue = "10", required = false) int size,
                                                             @RequestParam(defaultValue = "firstName") String searchTerm) {
        return new ResponseEntity<>(customerService.getCustomers(page, size, searchTerm), HttpStatus.OK);
    }


    @Operation(
            summary = GET_CUSTOMER_BY_ID,
            description = GET_CUSTOMER_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = CUSTOMER_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = CUSTOMER_FOUND)
    })
    @GetMapping("/id/{customerId}")
    public ResponseEntity<CustomerGetDto> getCustomer(@PathVariable("customerId")@Parameter(name = "Client Id",
           description =  "Customer id", example = "1") Long customerId) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
    }

    @Operation(
            summary = GET_CUSTOMER_BY_USERNAME,
            description = GET_CUSTOMER_BY_USERNAME
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = CUSTOMER_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = CUSTOMER_NOT_FOUND)})
    @GetMapping("/username/{username}")
    public ResponseEntity<CustomerGetDto> getCustomerByUsername(@PathVariable("username")@Parameter(name = "Client " +
            "Username", description = "Customer username", example = "joaquimverde") String username) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.getCustomerByUsername(username), HttpStatus.OK);
    }

    @Operation(
            summary = GET_CUSTOMER_FAVORITE_GENRES_BY_ID,
            description = GET_CUSTOMER_FAVORITE_GENRES_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = GENRES_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = CUSTOMER_NOT_FOUND)})
    @GetMapping("/favoriteGenres/{customerId}")
    public ResponseEntity<List<GenreCreateDto>> getFavoriteGenresById(@PathVariable ("customerId")@Parameter(name = "Client Id",
            description =  "Customer id", example = "1") Long customerId) throws CustomerNotFoundException{
    return new ResponseEntity<>(customerService.getFavoriteGenresById(customerId), HttpStatus.OK);
    }

    @Operation(
            summary = GET_CUSTOMER_FAVORITE_BOOKS_BY_ID,
            description = GET_CUSTOMER_FAVORITE_BOOKS_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = BOOKS_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = CUSTOMER_NOT_FOUND)})
    @GetMapping("/favoriteBooks/{customerId}")
    public ResponseEntity<List<BookGetDto>> getFavoriteBooksById(@PathVariable ("customerId")@Parameter(name = "Client Id",
            description =  "Customer id", example = "1") Long customerId) throws CustomerNotFoundException{
        return new ResponseEntity<>(customerService.getFavoriteBooksById(customerId),HttpStatus.OK );
    }


    @Operation(
            summary = ADD_NEW_CUSTOMER,
            description = ADD_NEW_CUSTOMER
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = CUSTOMER_CREATED),
            @ApiResponse(responseCode = CONFLICT, description = CUSTOMER_ALREADY_EXISTS),
            @ApiResponse(responseCode = NOT_FOUND, description = GENRE_NOT_FOUND)})
    @PostMapping("/")
    public ResponseEntity<CustomerGetDto> addNewCustomer(@Valid @RequestBody CustomerCreateDto customer) throws CustomerAlreadyExistsException, GenreNotFoundException {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @Operation(
            summary = ADD_MULTIPLE_CUSTOMERS,
            description = ADD_MULTIPLE_CUSTOMERS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = CUSTOMERS_CREATED),
            @ApiResponse(responseCode = CONFLICT, description = CUSTOMER_ALREADY_EXISTS),
            @ApiResponse(responseCode = NOT_FOUND, description = GENRE_NOT_FOUND)
    })
    @PostMapping("/addMultipleCustomers")
    public ResponseEntity<List<CustomerGetDto>> addNewCustomers(@Valid @RequestBody List<CustomerCreateDto> customer) throws CustomerAlreadyExistsException, GenreNotFoundException {
        return new ResponseEntity<>(customerService.createCustomers(customer), HttpStatus.CREATED);
    }

    @Operation(
            summary = ADD_FAVORITE_BOOKS,
            description = ADD_FAVORITE_BOOKS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = BOOK_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = CUSTOMER_OR_BOOK_NOT_FOUND),
            @ApiResponse(responseCode = CONFLICT, description = REPEATED_FAVORITE_BOOK)
    })
    @PostMapping("addToFavorites/{customerId}")
    public ResponseEntity<List<BookGetDto>> addBooksToFavorites(@PathVariable ("customerId")@Parameter(name = "Client Id",
            description =  "Customer id", example = "1") Long customerId,
                                                          @RequestBody CustomerFavoriteDto customerFavoriteDto) throws CustomerNotFoundException, BookNotFoundException, CustomerRepeatedFavoriteBooks {
        return new ResponseEntity<>(customerService.addToFavorites(customerId, customerFavoriteDto), HttpStatus.OK);
    }

    @Operation(
            summary = UPDATE_CUSTOMER,
            description = UPDATE_CUSTOMER
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = CUSTOMER_UPDATED),
            @ApiResponse(responseCode = NOT_FOUND, description = CUSTOMER_NOT_FOUND),
            @ApiResponse(responseCode = CONFLICT, description = CUSTOMER_ALREADY_EXISTS)
    })
    @PatchMapping("/id/{customerId}")
    public ResponseEntity<CustomerPatchDto> updateCustomer(@PathVariable ("customerId")@Parameter(name = "Client Id",
            description =  "Customer id", example = "1") Long customerId, @Valid @RequestBody CustomerPatchDto customerPatchDto) throws CustomerNotFoundException, CustomerWithEmailAlreadyExists {
        return new ResponseEntity<>(customerService.updateCustomer(customerId, customerPatchDto), HttpStatus.OK);
    }

    @Operation(
            summary = DELETE_CUSTOMER_BY_ID,
            description = DELETE_CUSTOMER_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = CUSTOMER_DELETED),
            @ApiResponse(responseCode = NOT_FOUND, description = CUSTOMER_NOT_FOUND)
    })
    @DeleteMapping("/id/{customerId}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable ("customerId")@Parameter(name = "Client Id",
            description =  "Customer id", example = "1") Long customerId) throws CustomerNotFoundException {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
