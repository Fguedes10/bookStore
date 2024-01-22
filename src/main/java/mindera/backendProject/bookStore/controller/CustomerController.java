package mindera.backendProject.bookStore.controller;

import io.swagger.models.Response;
import mindera.backendProject.bookStore.dtos.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.service.CustomerServiceImpl;
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
    public ResponseEntity<List<CustomerCreateDto>> getCustomers(){
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }


    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerCreateDto> getCustomer(@PathVariable("customerId") Long customerId){
        return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CustomerGetDto> addNewCustomer(@RequestBody CostumerGetDto client,
                                                         BindingResult bindingResult){
        return ResponseEntity<>(customerService.createCustomer(client), HttpStatus.CREATED);
    }


}
