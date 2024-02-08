package mindera.backendProject.bookStore.service.customerService;

import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerGetDto;
import mindera.backendProject.bookStore.dto.customer.CustomerPatchDto;
import mindera.backendProject.bookStore.exception.book.GenreNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerAlreadyExistsException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerWithEmailAlreadyExists;

import java.util.List;

public interface CustomerService {

    List<CustomerGetDto> getCustomers(int page, int size, String searchTerm);

    CustomerGetDto getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerGetDto createCustomer(CustomerCreateDto customerCreateDto) throws CustomerAlreadyExistsException, GenreNotFoundException;

    CustomerPatchDto updateCustomer(Long customerId, CustomerPatchDto customerPatchDto) throws CustomerNotFoundException, CustomerWithEmailAlreadyExists;


    void deleteCustomer(Long customerId) throws CustomerNotFoundException;

}
