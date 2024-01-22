package mindera.backendProject.bookStore.service;

import mindera.backendProject.bookStore.converter.CustomerConverter;
import mindera.backendProject.bookStore.dtos.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public List<CustomerCreateDto> getCustomers() {
        List<Customer> customersList = customerRepository.findAll();
        return customersList.stream().map(CustomerConverter::fromEntitytoCustomerCreateDto).toList();
    }

    @Override
    public CustomerCreateDto getCustomer(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException("Customer with id " + customerId + " doesn't exists");
        }
        return CustomerConverter.fromEntitytoCustomerCreateDto(customerOptional.get());
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
