package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.converter.order.OrderConverter;
import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderGetByBookDto;
import mindera.backendProject.bookStore.dto.order.OrderGetByCustomerDto;
import mindera.backendProject.bookStore.dto.order.OrderGetDto;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.OrderModel;
import mindera.backendProject.bookStore.repository.bookRepository.BookRepository;
import mindera.backendProject.bookStore.repository.customerRepository.CustomerRepository;
import mindera.backendProject.bookStore.repository.orderRepository.OrderRepository;
import mindera.backendProject.bookStore.service.bookService.BookServiceImpl;
import mindera.backendProject.bookStore.service.customerService.CustomerServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.*;


@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BookServiceImpl bookService;
    private final CustomerServiceImpl customerService;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    public OrderServiceImpl(OrderRepository orderRepository, BookServiceImpl bookService, CustomerServiceImpl customerService, CustomerRepository customerRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.bookService = bookService;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<OrderGetDto> getOrders() {
        List<OrderModel> orderList = orderRepository.findAll();
        return orderList.stream().map(OrderConverter::fromModelToOrderGetDto).toList();
    }

    @Override
    public OrderGetDto getOrder(Long orderModelId) throws OrderNotFoundException {
        Optional<OrderModel> orderModelOptional = verifyOrderExistsById(orderModelId);
        return OrderConverter.fromModelToOrderGetDto(orderModelOptional.get());
    }

    public List<OrderGetByCustomerDto> getOrderByCostumer(Long customerId) throws CustomerNotFoundException {
        if (customerId <= 0) {
            throw new CustomerNotFoundException(CUSTOMER_WITH_ID + customerId + DOESNT_EXIST);
        }
        Optional<Customer> getCustomer = customerRepository.findById(customerId);
        if (getCustomer.isEmpty()) {
            throw new CustomerNotFoundException(CUSTOMER_WITH_ID + customerId + DOESNT_EXIST);
        }
        List<OrderModel> findedOrders = orderRepository.findOrderByCustomer(customerId);
        if (findedOrders.isEmpty()) {
            throw new CustomerNotFoundException(NO_ORDER_WITH_CUSTOMER + customerId);
        }
        return orderRepository.findOrderByCustomer(customerId)
                .stream()
                .map(OrderConverter::fromModelToOderGetByCustomerDto)
                .toList();
    }

    public List<OrderGetByBookDto> getOrderByBook(Long bookId) throws BookNotFoundException {
        if (bookId <= 0) {
            throw new BookNotFoundException(BOOK_WITH_ID + bookId + DOESNT_EXIST);
        }
        Optional<Book> getBook = bookRepository.findById(bookId);
        if (getBook.isEmpty()) {
            throw new BookNotFoundException(BOOK_WITH_ID + bookId + DOESNT_EXIST);
        }
        List<OrderModel> findedOrders = orderRepository.getOrderModelsByBookId(bookId);
        if (findedOrders.isEmpty()) {
            throw new BookNotFoundException(NO_ORDER_WITH_BOOK + bookId);
        }
        return orderRepository.getOrderModelsByBookId(bookId)
                .stream()
                .map(OrderConverter::fromModelToOderGetByBookDto)
                .toList();
    }

    private Optional<OrderModel> verifyOrderExistsById(Long orderModelId) throws OrderNotFoundException {
        Optional<OrderModel> orderModelOptional = orderRepository.findById(orderModelId);
        if (orderModelOptional.isEmpty()) {
            throw new OrderNotFoundException(ORDERMODEL_WITH_ID + orderModelId + DOESNT_EXIST);
        }
        return orderModelOptional;
    }

    @Override
    public OrderGetDto createOrder(OrderCreateDto orderCreateDto, Long orderId) throws CustomerNotFoundException,
            OrderAlreadyExistsException, BookNotFoundException {
        Optional<OrderModel> orderModelFindById = orderRepository.findById(orderId);
        Customer customer = customerService.findById(orderCreateDto.customerId());
        List<Book> bookList = bookService.getBooksByIds(orderCreateDto.books());

        if (orderModelFindById.isPresent()) {
            throw new OrderAlreadyExistsException(ORDERMODEL_WITH_ID + orderId + ALREADY_EXISTS);
        }
        OrderModel newOrderModel = OrderConverter.fromCreateDtoToModel(orderCreateDto, customer, bookList);
        orderRepository.save(newOrderModel);
        return OrderConverter.fromModelToOrderGetDto(newOrderModel);
    }


    @Override
    public List<OrderGetDto> createOrders(List<OrderCreateDto> orderCreateDto, Long orderId) throws CustomerNotFoundException, BookNotFoundException, OrderNotFoundException {
        List<OrderGetDto> ordersCreated = new ArrayList<>();
        for (OrderCreateDto orderToCreate : orderCreateDto) {
            Customer customer = customerService.findById(orderToCreate.customerId());
            List<Book> bookList = bookService.getBooksByIds(orderToCreate.books());
            verifyOrderExistsById(orderId);
            OrderModel orderToSave = OrderConverter.fromCreateDtoToModel(orderToCreate, customer, bookList);
            orderRepository.save(orderToSave);
            ordersCreated.add(OrderConverter.fromModelToOrderGetDto(orderToSave));
        }
        return ordersCreated;
    }


    @Override
    public void deleteOrder(Long orderId) throws OrderNotFoundException {
        Optional<OrderModel> orderOptional = verifyOrderExistsById(orderId);
        orderRepository.delete(orderOptional.get());
    }

    public OrderModel findById(Long orderModelId) throws OrderNotFoundException {
        Optional<OrderModel> orderOptional = orderRepository.findById(orderModelId);
        if (orderOptional.isEmpty()) {
            throw new OrderNotFoundException(ORDERMODEL_WITH_ID + orderModelId + DOESNT_EXIST);
        }
        return orderOptional.get();
    }


}
