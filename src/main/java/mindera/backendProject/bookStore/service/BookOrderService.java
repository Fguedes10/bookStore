package mindera.backendProject.bookStore.service;

import mindera.backendProject.bookStore.model.BookOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookOrderService {

    Optional<BookOrder> getBookOrderById(Long id);

    List<BookOrder> getBookOrdersByCustomerId(Long customerId);

    //BookOrder createBookOrder(BookOrderCreateDto bookOrderCreateDto);

    boolean deleteBookOrder(Long id);
}
