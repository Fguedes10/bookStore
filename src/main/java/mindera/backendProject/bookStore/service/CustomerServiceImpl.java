package mindera.backendProject.bookStore.service;

import mindera.backendProject.bookStore.converter.CustomerConverter;
import mindera.backendProject.bookStore.dtos.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dtos.customer.CustomerPatchDto;
import mindera.backendProject.bookStore.exception.CustomerAlreadyExistsException;
import mindera.backendProject.bookStore.exception.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.CustomerWithEmailAlreadyExists;
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
    public CustomerCreateDto createCustomer(CustomerCreateDto customerCreateDto) throws CustomerAlreadyExistsException {
        Optional<Customer> customerFindByEmail = customerRepository.findByEmail(customerCreateDto);
        Optional<Customer> customerFindByNif = customerRepository.findByNif(customerCreateDto);
        Optional<Customer> customerFindByUsername= customerRepository.findByUsername(customerCreateDto);
        if(customerFindByEmail.isPresent() || customerFindByNif.isPresent() || customerFindByUsername.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already exists");
        }
        Customer customerToSave = CustomerConverter.fromCustomerCreateDtoToEntity(customerCreateDto);
        return customerCreateDto;
    }

    public CustomerPatchDto updateCustomer(Long customerId, CustomerPatchDto customerPatchDto) throws CustomerNotFoundException, CustomerWithEmailAlreadyExists {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()){
            throw new CustomerNotFoundException("Customer with id " + customerId + " doesn't exists");
        }
        Customer customerToPatch = customerOptional.get();
        if(customerPatchDto.firstName() != null && !customerPatchDto.firstName().isEmpty() && !customerPatchDto.firstName().equals(customerToPatch.getFirstName())){
            customerToPatch.setFirstName(customerPatchDto.firstName());
        }
        if(customerPatchDto.lastName() != null && !customerPatchDto.lastName().isEmpty() && !customerPatchDto.lastName().equals(customerToPatch.getLastName())){
            customerToPatch.setLastName(customerPatchDto.lastName());
        }
        if(customerPatchDto.email() != null && !customerPatchDto.email().isEmpty() && !customerPatchDto.email().equals(customerToPatch.getEmail())){
            Optional<Customer> customerFindByEmail = customerRepository.patchFindByEmail(customerPatchDto.email());
            if(customerFindByEmail.isPresent()){
                throw new CustomerWithEmailAlreadyExists("A customer with this email already exists");
            }
            customerToPatch.setEmail(customerPatchDto.email());
        }
        Customer customerToSave = customerRepository.save(customerToPatch);
        return CustomerConverter.fromEntityToCustomerPatchDto(customerToSave);

    }
/*    @Override
    public CustomerPutDto putCustomer(Long customerId, Customer customer) {
        return null;
    }*/

    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException("Customer with id " + customerId + " doesn't exists");
        }
        customerRepository.delete(customerOptional.get());
    }
}
