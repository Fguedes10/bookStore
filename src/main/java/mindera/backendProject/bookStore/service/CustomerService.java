package mindera.backendProject.bookStore.service;

import mindera.backendProject.bookStore.dtos.customer.CustomerCreateDto;

import java.util.List;

public interface CustomerService {

    List<CustomerCreateDto> getCustomers();

    CustomerCreateDto getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerAddDto createCustomer(CostumerCreateDto customerCreateDto) throws CustomerAlreadyExistsException;

    CustomerPutDto putCustomer(Long customerId, Customer customer);

    void deleteCustomer(Long customerId, Customer customer) throws CustomerNotFoundException;

}
