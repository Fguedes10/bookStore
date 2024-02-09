package mindera.backendProject.bookStore.service.orderService;

import com.itextpdf.text.DocumentException;
import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderGetDto;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.InvoiceNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import mindera.backendProject.bookStore.exception.order.PdfNotFoundException;

import java.io.FileNotFoundException;
import java.util.List;


public interface OrderService {
    OrderGetDto getOrder(Long orderId) throws OrderNotFoundException;


    OrderGetDto createOrder(OrderCreateDto order, Long orderId) throws CustomerNotFoundException, OrderAlreadyExistsException, InvoiceNotFoundException, BookNotFoundException, DocumentException, FileNotFoundException, PdfNotFoundException;


    void deleteOrder(Long orderId) throws OrderNotFoundException;

    List<OrderGetDto> getOrders();
}
