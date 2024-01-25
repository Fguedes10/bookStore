package mindera.backendProject.bookStore.service;

import mindera.backendProject.bookStore.model.BookOrder;
import mindera.backendProject.bookStore.repository.BookOrderRepository;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookOrderServiceImpl implements BookOrderService {

    private final BookOrderRepository bookOrderRepository;
    private final CustomerRepository customerRepository;

    public BookOrderServiceImpl(BookOrderRepository bookOrderRepository, CustomerRepository customerRepository) {
        this.bookOrderRepository = bookOrderRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<BookOrder> getBookOrderById(Long id) {
        return bookOrderRepository.findById(id);
    }

    @Override
    public List<BookOrder> getBookOrdersByCustomerId(Long customerId) {
        return null; //ALTERAR
    }

    /*@Override
    public BookOrder createBookOrder(BookOrderCreateDto bookOrderCreateDto) {
        return null;
    }*/

    @Override
    public boolean deleteBookOrder(Long id) {
        return false;
    }
}
