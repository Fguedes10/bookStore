package mindera.backendProject.bookStore.service;

import mindera.backendProject.bookStore.dtos.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dtos.customer.CustomerPatchDto;
import mindera.backendProject.bookStore.exception.CustomerAlreadyExistsException;
import mindera.backendProject.bookStore.exception.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.CustomerWithEmailAlreadyExists;
import mindera.backendProject.bookStore.model.Customer;

import java.util.List;

public interface CustomerService {

    List<CustomerCreateDto> getCustomers();

    CustomerCreateDto getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerCreateDto createCustomer(CustomerCreateDto customerCreateDto) throws CustomerAlreadyExistsException;

    CustomerPatchDto updateCustomer(Long customerId, CustomerPatchDto customerPatchDto) throws CustomerNotFoundException, CustomerWithEmailAlreadyExists;

   // CustomerPutDto putCustomer(Long customerId, Customer customer);

    void deleteCustomer(Long customerId) throws CustomerNotFoundException;

}
