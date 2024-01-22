package mindera.backendProject.bookStore.service;

import mindera.backendProject.bookStore.repository.CustomerRepository;

public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public List<CustomerGetAllDto> getCustomers() {
        return null;
    }

    @Override
    public CustomerGetDto getCustomer(Long customerId) throws CustomerNotFoundException {
        return null;
    }

    @Override
    public CustomerAddDto createCustomer(CostumerCreateDto customerCreateDto) throws CustomerAlreadyExistsException {
        return null;
    }

    @Override
    public CustomerPutDto putCustomer(Long customerId, Customer customer) {
        return null;
    }

    @Override
    public void deleteCustomer(Long customerId, Customer customer) throws CustomerNotFoundException {

    }
}
