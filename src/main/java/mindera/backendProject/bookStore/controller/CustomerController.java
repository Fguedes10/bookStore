package mindera.backendProject.bookStore.controller;

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

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {


    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerGetDto>> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }

    @GetMapping("/id/{customerId}")
    public ResponseEntity<CustomerGetDto> getCustomer(@PathVariable("customerId") Long customerId) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<CustomerGetDto> getCustomerByUsername(@PathVariable("username") String username) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.getCustomerByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/favoriteGenres/{customerId}")
    public ResponseEntity<List<GenreCreateDto>> getFavoriteGenresById(@PathVariable ("customerId") Long customerId) throws CustomerNotFoundException{
    return new ResponseEntity<>(customerService.getFavoriteGenresById(customerId), HttpStatus.OK);
    }

    @GetMapping("/favoriteBooks/{customerId}")
    public ResponseEntity<List<BookGetDto>> getFavoriteBooksById(@PathVariable ("customerId") Long customerId) throws CustomerNotFoundException{
        return new ResponseEntity<>(customerService.getFavoriteBooksById(customerId),HttpStatus.OK );
    }


    @PostMapping("/")
    public ResponseEntity<CustomerGetDto> addNewCustomer(@Valid @RequestBody CustomerCreateDto customer) throws CustomerAlreadyExistsException, GenreNotFoundException {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }
    @PostMapping("/addMany")
    public ResponseEntity<List<CustomerGetDto>> addNewCustomers(@Valid @RequestBody List<CustomerCreateDto> customer) throws CustomerAlreadyExistsException, GenreNotFoundException {
        return new ResponseEntity<>(customerService.createCustomers(customer), HttpStatus.CREATED);
    }

    @PostMapping("addToFavorites/{customerId}")
    public ResponseEntity<List<BookGetDto>> addBooksToFavorites(@PathVariable ("customerId") Long customerId,
                                                          @RequestBody CustomerFavoriteDto customerFavoriteDto) throws CustomerNotFoundException, BookNotFoundException, CustomerRepeatedFavoriteBooks {
        return new ResponseEntity<>(customerService.addToFavorites(customerId, customerFavoriteDto), HttpStatus.OK);
    }

    @PatchMapping("/id/{customerId}")
    public ResponseEntity<CustomerPatchDto> updateCustomer(@PathVariable ("customerId") Long customerId, @Valid @RequestBody CustomerPatchDto customerPatchDto) throws CustomerNotFoundException, CustomerWithEmailAlreadyExists {
        return new ResponseEntity<>(customerService.updateCustomer(customerId, customerPatchDto), HttpStatus.OK);
    }

    @DeleteMapping("/id/{customerId}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable ("customerId") Long customerId) throws CustomerNotFoundException {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
