package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.converter.order.OrderItemConverter;
import mindera.backendProject.bookStore.dto.order.OrderItemCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderItemGetDto;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderItemAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.OrderItemNotFoundException;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.OrderItem;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import mindera.backendProject.bookStore.repository.orderRepository.OrderItemRepository;
import mindera.backendProject.bookStore.service.bookService.BookServiceImpl;
import mindera.backendProject.bookStore.service.customerService.CustomerServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final BookServiceImpl bookService;
    private final CustomerServiceImpl customerService;

    private final CustomerRepository customerRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, BookServiceImpl bookService, CustomerServiceImpl customerService, CustomerRepository customerRepository) {
        this.orderItemRepository = orderItemRepository;
        this.bookService = bookService;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<OrderItemGetDto> getOrderItems() {
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        return orderItemList.stream().map(OrderItemConverter::fromModelToOrderITemGetDto).toList();
    }

    @Override
    public OrderItemGetDto getOrderItem(Long orderItemId) throws OrderItemNotFoundException {
        Optional<OrderItem> orderItemOptional = verifyOrderItemExistsById(orderItemId);
        return OrderItemConverter.fromModelToOrderITemGetDto(orderItemOptional.get());
    }


    private Optional<OrderItem> verifyOrderItemExistsById(Long orderItemId) throws OrderItemNotFoundException {
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(orderItemId);
        if (orderItemOptional.isEmpty()) {
            throw new OrderItemNotFoundException(ORDERITEM_WITH_ID + orderItemId + DOESNT_EXIST);
        }
        return orderItemOptional;
    }

    @Override
    public OrderItemGetDto createOrderItem(OrderItemCreateDto orderItemCreateDto, Long customerId) throws CustomerNotFoundException, BookNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException(CUSTOMER_WITH_ID + customerId + DOESNT_EXIST);
        }
        Customer customer = customerOptional.get();
        List<Book> bookList = bookService.getBooksByIds(orderItemCreateDto.books());
        OrderItem newOrderItem = OrderItemConverter.fromCreateDtoToModel(bookList);
        orderItemRepository.save(newOrderItem);
        return OrderItemConverter.fromModelToOrderITemGetDto(newOrderItem);
    }

    @Override
    public List<OrderItemGetDto> createOrderItems(List<OrderItemCreateDto> orderItemCreateDto, Long orderItemId) throws CustomerNotFoundException, OrderItemNotFoundException, BookNotFoundException {
        List<OrderItemGetDto> orderItemsCreated = new ArrayList<>();
        for (OrderItemCreateDto orderItemToCreate : orderItemCreateDto) {
            List<Book> bookList = bookService.getBooksByIds(orderItemToCreate.books());
            verifyOrderItemExistsById(orderItemId);
            OrderItem orderItemToSave = OrderItemConverter.fromCreateDtoToModel(bookList);
            orderItemRepository.save(orderItemToSave);
            orderItemsCreated.add(OrderItemConverter.fromModelToOrderITemGetDto(orderItemToSave));
        }
        return orderItemsCreated;

    }

    @Override
    public void deleteOrderItem(Long orderItemId) throws OrderItemNotFoundException {
        Optional<OrderItem> orderItemOptional = verifyOrderItemExistsById(orderItemId);
        orderItemRepository.delete(orderItemOptional.get());
    }

    public OrderItem findById(Long orderItemId) throws OrderItemAlreadyExistsException {
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(orderItemId);
        if (orderItemOptional.isEmpty()) {
            throw new OrderItemAlreadyExistsException(ORDERITEM_WITH_ID + orderItemId + DOESNT_EXIST);
        }
        return orderItemOptional.get();
    }
}
