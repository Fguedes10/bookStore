package mindera.backendProject.bookStore.service;

public interface CustomerService {

    List<CustomerGetAllDto> getCustomers();

    CustomerGetDto getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerAddDto createCustomer(CostumerCreateDto customerCreateDto) throws CustomerAlreadyExistsException;

    CustomerPutDto putCustomer(Long customerId, Customer customer);

    void deleteCustomer(Long customerId, Customer customer) throws CustomerNotFoundException;

}
