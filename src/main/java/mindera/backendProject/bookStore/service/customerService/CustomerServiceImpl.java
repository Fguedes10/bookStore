package mindera.backendProject.bookStore.service.customerService;

import mindera.backendProject.bookStore.converter.book.BookConverter;
import mindera.backendProject.bookStore.converter.book.GenreConverter;
import mindera.backendProject.bookStore.converter.customer.CustomerConverter;
import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerFavoriteDto;
import mindera.backendProject.bookStore.dto.customer.CustomerGetDto;
import mindera.backendProject.bookStore.dto.customer.CustomerPatchDto;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.book.GenreNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerAlreadyExistsException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerRepeatedFavoriteBooks;
import mindera.backendProject.bookStore.exception.customer.CustomerWithEmailAlreadyExists;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.Genre;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import mindera.backendProject.bookStore.service.bookService.BookServiceImpl;
import mindera.backendProject.bookStore.service.bookService.GenreServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final GenreServiceImpl genreService;

    private final BookServiceImpl bookService;

    public CustomerServiceImpl(CustomerRepository customerRepository, GenreServiceImpl genreService, BookServiceImpl bookService) {
        this.customerRepository = customerRepository;
        this.genreService = genreService;
        this.bookService = bookService;
    }


    @Override
    public List<CustomerGetDto> getCustomers(int page, int size, String searchTerm) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, searchTerm);
        Page<Customer> customersList = customerRepository.findAll(pageRequest);
        return customersList.stream().map(CustomerConverter::fromEntityToCustomerGetDto).toList();
    }

    @Override
    public CustomerGetDto getCustomer(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = verifyCustomerExistsById(customerId);
        return CustomerConverter.fromEntityToCustomerGetDto(customerOptional.get());
    }

    public CustomerGetDto getCustomerByUsername(String username) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException(CUSTOMER_WITH_USERNAME + username + DOESNT_EXIST);
        }
        return CustomerConverter.fromEntityToCustomerGetDto(customerOptional.get());
    }

    @Override
    public CustomerGetDto createCustomer(CustomerCreateDto customerCreateDto) throws CustomerAlreadyExistsException, GenreNotFoundException {
        verifyIfCustomerExists(customerCreateDto);
        List<Genre> genreList = genreService.findByIds(customerCreateDto.favoriteGenresIds());
        Customer customerToSave = CustomerConverter.fromCustomerCreateDtoToEntity(customerCreateDto, genreList);
        customerRepository.save(customerToSave);
        return CustomerConverter.fromEntityToCustomerGetDto(customerToSave);
    }

    public List<CustomerGetDto> createCustomers(List<CustomerCreateDto> customerCreateDto) throws CustomerAlreadyExistsException, GenreNotFoundException {
        List<CustomerGetDto> customersCreated = new ArrayList<>();
        for (CustomerCreateDto customerToCreate : customerCreateDto) {
            List<Genre> genreList = genreService.findByIds(customerToCreate.favoriteGenresIds());
            verifyIfCustomerExists(customerToCreate);
            Customer customerToSave = CustomerConverter.fromCustomerCreateDtoToEntity(customerToCreate, genreList);
            customerRepository.save(customerToSave);
            customersCreated.add(CustomerConverter.fromEntityToCustomerGetDto(customerToSave));
        }
        return customersCreated;
    }

    public void verifyIfCustomerExists(CustomerCreateDto customerToCreate) throws CustomerAlreadyExistsException {
        Optional<Customer> customerFindByEmail = customerRepository.findByEmail(customerToCreate.email());
        Optional<Customer> customerFindByNif = customerRepository.findByNif(customerToCreate.nif());
        Optional<Customer> customerFindByUsername = customerRepository.findByUsername(customerToCreate.username());
        if (customerFindByUsername.isPresent()) {
            throw new CustomerAlreadyExistsException(CUSTOMER_WITH_USERNAME + customerToCreate.username() + ALREADY_EXISTS);
        }
        if (customerFindByEmail.isPresent()) {
            throw new CustomerAlreadyExistsException(CUSTOMER_WITH_EMAIL + customerToCreate.email() + ALREADY_EXISTS);
        }
        if (customerFindByNif.isPresent()) {
            throw new CustomerAlreadyExistsException(CUSTOMER_WITH_NIF + customerToCreate.nif() + ALREADY_EXISTS);
        }
    }

    @Override
    public CustomerPatchDto updateCustomer(Long customerId, CustomerPatchDto customerPatchDto) throws CustomerNotFoundException, CustomerWithEmailAlreadyExists {
        Optional<Customer> customerOptional = verifyCustomerExistsById(customerId);
        Customer customerToPatch = customerOptional.get();
        if (customerPatchDto.firstName() != null && !customerPatchDto.firstName().isEmpty() && !customerPatchDto.firstName().equals(customerToPatch.getFirstName())) {
            customerToPatch.setFirstName(customerPatchDto.firstName());
        }
        if (customerPatchDto.lastName() != null && !customerPatchDto.lastName().isEmpty() && !customerPatchDto.lastName().equals(customerToPatch.getLastName())) {
            customerToPatch.setLastName(customerPatchDto.lastName());
        }
        if (customerPatchDto.email() != null && !customerPatchDto.email().isEmpty() && !customerPatchDto.email().equals(customerToPatch.getEmail())) {
            Optional<Customer> customerFindByEmail = customerRepository.findByEmail(customerPatchDto.email());
            if (customerFindByEmail.isPresent()) {
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
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException(CUSTOMER_WITH_ID + customerId + DOESNT_EXIST);
        }
        return customerOptional;
    }

    public List<GenreCreateDto> getFavoriteGenresById(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = verifyCustomerExistsById(customerId);
        List<Genre> favoriteGenres = customerOptional.get().getFavoriteGenres();
        return GenreConverter.fromEntityToCreateDto(favoriteGenres);
    }

    public List<BookGetDto> getFavoriteBooksById(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = verifyCustomerExistsById(customerId);
        List<Book> favoriteBooks = customerOptional.get().getFavoriteBooks();
        return BookConverter.fromModelToBookGetFavoriteBooksDto(favoriteBooks);
    }

    public List<BookGetDto> addToFavorites(Long customerId, CustomerFavoriteDto customerFavoriteDto) throws CustomerNotFoundException, BookNotFoundException, CustomerRepeatedFavoriteBooks {
        Optional<Customer> customerOptional = verifyCustomerExistsById(customerId);
        Set<Long> uniqueBookIds = new HashSet<>(customerFavoriteDto.bookIds());
        List<Book> favoritesBookList = bookService.getBooksByIds(new ArrayList<>(uniqueBookIds));
        for (Book book : favoritesBookList) {
            boolean isBookAlreadyAdded =
                    customerOptional.get().getFavoriteBooks().stream().anyMatch(existingBook -> existingBook.equals(book));
            if (isBookAlreadyAdded) {
                throw new CustomerRepeatedFavoriteBooks("The books with id " + book.getId() + " is already in" +
                        " " +
                        "favorites.");
            }
            customerOptional.get().getFavoriteBooks().add(book);
        }
        customerRepository.save(customerOptional.get());
        return BookConverter.fromModelToBookGetFavoriteBooksDto(customerOptional.get().getFavoriteBooks());
    }

    public Customer findById(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException(CUSTOMER_WITH_ID + customerId + DOESNT_EXIST);
        }
        return customerOptional.get();
    }

}




