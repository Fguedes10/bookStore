package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderGetDto;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.InvoiceNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {
    OrderGetDto getOrder(Long orderId) throws OrderNotFoundException;


  OrderGetDto createOrder(OrderCreateDto orderCreateDto, Long orderId) throws CustomerNotFoundException, OrderAlreadyExistsException, InvoiceNotFoundException, BookNotFoundException;

  List<OrderGetDto> createOrders(List<OrderCreateDto> orderCreateDto, Long orderID) throws CustomerNotFoundException, InvoiceNotFoundException, BookNotFoundException, OrderAlreadyExistsException, OrderNotFoundException;

    void deleteOrder(Long orderId) throws OrderNotFoundException;

    List<OrderGetDto> getOrders();
}
