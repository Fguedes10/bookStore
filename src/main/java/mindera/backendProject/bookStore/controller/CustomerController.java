package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.dto.book.CustomerFavoriteDto;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerGetDto;
import mindera.backendProject.bookStore.dto.customer.CustomerPatchDto;
import mindera.backendProject.bookStore.exception.*;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.service.customerService.CustomerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "Customer", description = "Customer endpoints")
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {


    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }


    @Operation(
            summary = "Get all existing customers",
            description = "Get all customers"
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get all customers")})
            @GetMapping("/")
    public ResponseEntity<List<CustomerGetDto>> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }


    @Operation(
            summary = "Get customer by id",
            description = "Get customer by id"
    )
    @GetMapping("/id/{customerId}")
    public ResponseEntity<CustomerGetDto> getCustomer(@PathVariable("customerId")@Parameter(name = "Client Id",
           description =  "Customer id", example = "1") Long customerId) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get customer by username",
            description = "Get customer by username"
    )
    @GetMapping("/username/{username}")
    public ResponseEntity<CustomerGetDto> getCustomerByUsername(@PathVariable("username")@Parameter(name = "Client " +
            "Username", description = "Customer username", example = "joaquimverde") String username) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.getCustomerByUsername(username), HttpStatus.OK);
    }

    @Operation(
            summary = "Get customer favorite genres by id",
            description = "Get customer favorite genres by id"
    )
    @GetMapping("/favoriteGenres/{customerId}")
    public ResponseEntity<List<GenreCreateDto>> getFavoriteGenresById(@PathVariable ("customerId")@Parameter(name = "Client Id",
            description =  "Customer id", example = "1") Long customerId) throws CustomerNotFoundException{
    return new ResponseEntity<>(customerService.getFavoriteGenresById(customerId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get customer favorite books by id",
            description = "Get customer favorite books by id"
    )
    @GetMapping("/favoriteBooks/{customerId}")
    public ResponseEntity<List<BookGetDto>> getFavoriteBooksById(@PathVariable ("customerId")@Parameter(name = "Client Id",
            description =  "Customer id", example = "1") Long customerId) throws CustomerNotFoundException{
        return new ResponseEntity<>(customerService.getFavoriteBooksById(customerId),HttpStatus.OK );
    }


    @Operation(
            summary = "Add new customer",
            description = "Add new customer"
    )
    @PostMapping("/")
    public ResponseEntity<CustomerGetDto> addNewCustomer(@Valid @RequestBody CustomerCreateDto customer) throws CustomerAlreadyExistsException, GenreNotFoundException {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Add a list of new customers",
            description = "Add a list of new customers"
    )
    @PostMapping("/addMultipleCustomers")
    public ResponseEntity<List<CustomerGetDto>> addNewCustomers(@Valid @RequestBody List<CustomerCreateDto> customer) throws CustomerAlreadyExistsException, GenreNotFoundException {
        return new ResponseEntity<>(customerService.createCustomers(customer), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Add books to customer favorites",
            description = "Add books to customer favorites"
    )
    @PostMapping("addToFavorites/{customerId}")
    public ResponseEntity<List<BookGetDto>> addBooksToFavorites(@PathVariable ("customerId")@Parameter(name = "Client Id",
            description =  "Customer id", example = "1") Long customerId,
                                                          @RequestBody CustomerFavoriteDto customerFavoriteDto) throws CustomerNotFoundException, BookNotFoundException, CustomerRepeatedFavoriteBooks {
        return new ResponseEntity<>(customerService.addToFavorites(customerId, customerFavoriteDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Update customer",
            description = "Update customer"
    )
    @PatchMapping("/id/{customerId}")
    public ResponseEntity<CustomerPatchDto> updateCustomer(@PathVariable ("customerId")@Parameter(name = "Client Id",
            description =  "Customer id", example = "1") Long customerId, @Valid @RequestBody CustomerPatchDto customerPatchDto) throws CustomerNotFoundException, CustomerWithEmailAlreadyExists {
        return new ResponseEntity<>(customerService.updateCustomer(customerId, customerPatchDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete customer",
            description = "Delete customer"
    )
    @DeleteMapping("/id/{customerId}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable ("customerId")@Parameter(name = "Client Id",
            description =  "Customer id", example = "1") Long customerId) throws CustomerNotFoundException {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
