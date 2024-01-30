package mindera.backendProject.bookStore.service.customerService;

import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerGetDto;
import mindera.backendProject.bookStore.dto.customer.CustomerPatchDto;
import mindera.backendProject.bookStore.exception.*;

import java.util.List;

public interface CustomerService {

    List<CustomerGetDto> getCustomers();

    CustomerGetDto getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerGetDto createCustomer(CustomerCreateDto customerCreateDto) throws CustomerAlreadyExistsException, GenreNotFoundException;

    CustomerPatchDto updateCustomer(Long customerId, CustomerPatchDto customerPatchDto) throws CustomerNotFoundException, CustomerWithEmailAlreadyExists;


    void deleteCustomer(Long customerId) throws CustomerNotFoundException;

}
