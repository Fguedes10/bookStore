package mindera.backendProject.bookStore.service.customerService;

import mindera.backendProject.bookStore.converter.CustomerConverter;
import mindera.backendProject.bookStore.converter.GenreConverter;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerFavoriteGenresDto;
import mindera.backendProject.bookStore.dto.customer.CustomerGetDto;
import mindera.backendProject.bookStore.dto.customer.CustomerPatchDto;
import mindera.backendProject.bookStore.exception.CustomerAlreadyExistsException;
import mindera.backendProject.bookStore.exception.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.CustomerWithEmailAlreadyExists;
import mindera.backendProject.bookStore.exception.GenreNotFoundException;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.Genre;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import mindera.backendProject.bookStore.service.bookService.GenreServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    private final GenreServiceImpl genreService;

    public CustomerServiceImpl(CustomerRepository customerRepository, GenreServiceImpl genreService) {
        this.customerRepository = customerRepository;
        this.genreService = genreService;
    }


    @Override
    public List<CustomerGetDto> getCustomers() {
        List<Customer> customersList = customerRepository.findAll();
        return customersList.stream().map(CustomerConverter::fromEntityToCustomerGetDto).toList();
    }

    @Override
    public CustomerGetDto getCustomer(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = verifyCustomerExistsById(customerId);
        return CustomerConverter.fromEntityToCustomerGetDto(customerOptional.get());
    }
    public CustomerGetDto getCustomerByUsername(String username) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException(CUSTOMER_WITH_USERNAME + username + DOESNT_EXIST);
        }
        return CustomerConverter.fromEntityToCustomerGetDto(customerOptional.get());
    }

    @Override
    public CustomerGetDto createCustomer(CustomerCreateDto customerCreateDto) throws CustomerAlreadyExistsException, GenreNotFoundException {
        List<Genre> genreList = genreService.findByIds(customerCreateDto.favoriteGenresIds());
        verifyIfCustomerExists(customerCreateDto);
        Customer customerToSave = CustomerConverter.fromCustomerCreateDtoToEntity(customerCreateDto, genreList);
        customerRepository.save(customerToSave);
        return CustomerConverter.fromEntityToCustomerGetDto(customerToSave);
    }

    public List<CustomerGetDto> createCustomers(List<CustomerCreateDto> customerCreateDto) throws CustomerAlreadyExistsException, GenreNotFoundException {
        List<CustomerGetDto> customersCreated = new ArrayList<>();
        for(CustomerCreateDto customerToCreate : customerCreateDto){
            List<Genre> genreList = genreService.findByIds(customerToCreate.favoriteGenresIds());
            verifyIfCustomerExists(customerToCreate);
            Customer customerToSave = CustomerConverter.fromCustomerCreateDtoToEntity(customerToCreate, genreList);
            customerRepository.save(customerToSave);
            customersCreated.add(CustomerConverter.fromEntityToCustomerGetDto(customerToSave));
        }
        return customersCreated;
    }

    private void verifyIfCustomerExists(CustomerCreateDto customerToCreate) throws CustomerAlreadyExistsException {
        Optional<Customer> customerFindByEmail = customerRepository.findByEmail(customerToCreate.email());
        Optional<Customer> customerFindByNif = customerRepository.findByNif(customerToCreate.nif());
        Optional<Customer> customerFindByUsername= customerRepository.findByUsername(customerToCreate.username());
        if(customerFindByUsername.isPresent()){
            throw new CustomerAlreadyExistsException(CUSTOMER_WITH_USERNAME + customerToCreate.username() + ALREADY_EXISTS );
        }
        if(customerFindByEmail.isPresent()){
            throw new CustomerAlreadyExistsException(CUSTOMER_WITH_EMAIL + customerToCreate.email() + ALREADY_EXISTS);
        }
        if(customerFindByNif.isPresent()){
            throw new CustomerAlreadyExistsException(CUSTOMER_WITH_NIF + customerToCreate.nif() + ALREADY_EXISTS);
        }
    }

    @Override
    public CustomerPatchDto updateCustomer(Long customerId, CustomerPatchDto customerPatchDto) throws CustomerNotFoundException, CustomerWithEmailAlreadyExists {
        Optional<Customer> customerOptional = verifyCustomerExistsById(customerId);
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
        Optional<Customer> customerOptional = verifyCustomerExistsById(customerId);
        customerRepository.delete(customerOptional.get());
    }

    private Optional<Customer> verifyCustomerExistsById(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException(CUSTOMER_WITH_ID + customerId + DOESNT_EXIST);
        }
        return customerOptional;
    }

    public List<GenreCreateDto> getFavoriteById(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = verifyCustomerExistsById(customerId);
        List<Genre> favoriteGenres = customerOptional.get().getFavoriteGenres();
        return GenreConverter.fromEntityToCreateDto(favoriteGenres);

    }
}
