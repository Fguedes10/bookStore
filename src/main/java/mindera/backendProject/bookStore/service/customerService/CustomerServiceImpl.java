package mindera.backendProject.bookStore.service.customerService;

import mindera.backendProject.bookStore.converter.CustomerConverter;
import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerPatchDto;
import mindera.backendProject.bookStore.exception.CustomerAlreadyExistsException;
import mindera.backendProject.bookStore.exception.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.CustomerWithEmailAlreadyExists;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


import static mindera.backendProject.bookStore.util.Messages.*;

@Service
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
            throw new CustomerNotFoundException(CUSTOMER_WITH_ID + customerId + DOESNT_EXIST);
        }
        return CustomerConverter.fromEntitytoCustomerCreateDto(customerOptional.get());
    }
    public CustomerCreateDto getCustomerByUsername(String username) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException(CUSTOMER_WITH_USERNAME + username + DOESNT_EXIST);
        }
        return CustomerConverter.fromEntitytoCustomerCreateDto(customerOptional.get());
    }

    @Override
    public CustomerCreateDto createCustomer(CustomerCreateDto customerCreateDto) throws CustomerAlreadyExistsException {
        Optional<Customer> customerFindByEmail = customerRepository.findByEmail(customerCreateDto.email());
        Optional<Customer> customerFindByNif = customerRepository.findByNif(customerCreateDto.nif());
        Optional<Customer> customerFindByUsername= customerRepository.findByUsername(customerCreateDto.username());
        if(customerFindByUsername.isPresent()){
            throw new CustomerAlreadyExistsException(CUSTOMER_WITH_USERNAME + customerCreateDto.username() + ALREADY_EXISTS );
        }
        if(customerFindByEmail.isPresent()){
            throw new CustomerAlreadyExistsException(CUSTOMER_WITH_EMAIL + customerCreateDto.email() + ALREADY_EXISTS);
        }
        if(customerFindByNif.isPresent()){
            throw new CustomerAlreadyExistsException(CUSTOMER_WITH_NIF + customerCreateDto.nif() + ALREADY_EXISTS);
        }
        Customer customerToSave = CustomerConverter.fromCustomerCreateDtoToEntity(customerCreateDto);
        customerRepository.save(customerToSave);
        return customerCreateDto;
    }
    @Override
    public CustomerPatchDto updateCustomer(Long customerId, CustomerPatchDto customerPatchDto) throws CustomerNotFoundException, CustomerWithEmailAlreadyExists {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()){
            throw new CustomerNotFoundException(CUSTOMER_WITH_ID + customerId + DOESNT_EXIST);
        }
        Customer customerToPatch = customerOptional.get();
        if(customerPatchDto.firstName() != null && !customerPatchDto.firstName().isEmpty() && !customerPatchDto.firstName().equals(customerToPatch.getFirstName())){
            customerToPatch.setFirstName(customerPatchDto.firstName());
        }
        if(customerPatchDto.lastName() != null && !customerPatchDto.lastName().isEmpty() && !customerPatchDto.lastName().equals(customerToPatch.getLastName())){
            customerToPatch.setLastName(customerPatchDto.lastName());
        }
        if(customerPatchDto.email() != null && !customerPatchDto.email().isEmpty() && !customerPatchDto.email().equals(customerToPatch.getEmail())){
            Optional<Customer> customerFindByEmail = customerRepository.findByEmail(customerPatchDto.email());
            if(customerFindByEmail.isPresent()){
                throw new CustomerWithEmailAlreadyExists(CUSTOMER_EMAIL_ALREADY_EXISTS);
            }
            customerToPatch.setEmail(customerPatchDto.email());
        }
        Customer customerToSave = customerRepository.save(customerToPatch);
        return CustomerConverter.fromEntityToCustomerPatchDto(customerToSave);

    }


    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException(CUSTOMER_WITH_ID + customerId + DOESNT_EXIST);
        }
        customerRepository.delete(customerOptional.get());
    }
}
