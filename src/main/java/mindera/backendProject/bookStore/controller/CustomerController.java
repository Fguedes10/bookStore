package mindera.backendProject.bookStore.controller;

import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerPatchDto;
import mindera.backendProject.bookStore.exception.CustomerAlreadyExistsException;
import mindera.backendProject.bookStore.exception.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.CustomerWithEmailAlreadyExists;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.service.customerService.CustomerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {


    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerCreateDto>> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }

    @GetMapping("/id/{customerId}")
    public ResponseEntity<CustomerCreateDto> getCustomer(@PathVariable("customerId") Long customerId) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<CustomerCreateDto> getCustomerByUsername(@PathVariable("username") String username) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.getCustomerByUsername(username), HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<CustomerCreateDto> addNewCustomer(@Valid @RequestBody CustomerCreateDto customer,
                                                            BindingResult bindingResult) throws CustomerAlreadyExistsException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }
    @PostMapping("/addMany")
    public ResponseEntity<List<CustomerCreateDto>> addNewCustomers(@Valid @RequestBody List<CustomerCreateDto> customer,
                                                            BindingResult bindingResult) throws CustomerAlreadyExistsException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerService.createCustomers(customer), HttpStatus.CREATED);
    }



    @PatchMapping("/id/{customerId}")
    public ResponseEntity<CustomerPatchDto> updateCustomer(@PathVariable ("customerId") Long customerId, @Valid @RequestBody CustomerPatchDto customerPatchDto, BindingResult bindingResult) throws CustomerNotFoundException, CustomerWithEmailAlreadyExists {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerService.updateCustomer(customerId, customerPatchDto), HttpStatus.OK);
    }

    @DeleteMapping("/id/{customerId}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable ("customerId") Long customerId) throws CustomerNotFoundException {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
