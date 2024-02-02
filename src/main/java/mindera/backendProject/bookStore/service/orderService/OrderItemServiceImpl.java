package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.converter.order.OrderConverter;
import mindera.backendProject.bookStore.converter.order.OrderItemConverter;
import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderGetDto;
import mindera.backendProject.bookStore.dto.order.OrderItemCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderItemGetDto;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.OrderItemAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.OrderItemNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.OrderItem;
import mindera.backendProject.bookStore.model.OrderModel;
import mindera.backendProject.bookStore.repository.orderRepository.OrderItemRepository;
import mindera.backendProject.bookStore.service.bookService.BookServiceImpl;
import mindera.backendProject.bookStore.service.customerService.CustomerServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final BookServiceImpl bookService;
    private final CustomerServiceImpl customerService;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, BookServiceImpl bookService, CustomerServiceImpl customerService) {
        this.orderItemRepository = orderItemRepository;
        this.bookService = bookService;
        this.customerService = customerService;
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

 /*  @Override
    public OrderItemGetDto createOrderItem(OrderItemCreateDto orderItemCreateDto, Long orderItemId) throws CustomerNotFoundException, OrderItemAlreadyExistsException {
            Optional<OrderItem> orderItemFindById = orderItemRepository.findById(orderItemId);
            Customer customer = customerService.findById(orderItemCreateDto.customerId());
            Set<Long> bookList = bookService.getBooksByIds(orderItemCreateDto.books());

            if (orderItemFindById.isPresent()) {
                throw new OrderItemAlreadyExistsException(ORDERITEM_WITH_ID + orderItemId + ALREADY_EXISTS);
            }
            OrderItem newOrderItem = OrderItemConverter.fromCreateDtoToModel(orderItemCreateDto, customer, bookList);
            orderItemRepository.save(newOrderItem);
            return OrderItemConverter.fromModelToOrderITemGetDto(newOrderItem);
    }

    @Override
    public List<OrderItemGetDto> createOrderItems(List<OrderItemCreateDto> orderItemCreateDto, Long orderItemId) throws CustomerNotFoundException, OrderItemNotFoundException {
        List<OrderItemGetDto> orderItemsCreated = new ArrayList<>();
        for (OrderItemCreateDto orderItemToCreate : orderItemCreateDto) {
            Customer customer = customerService.findById(orderItemToCreate.customerId());
            Set<Long> bookList = bookService.getBooksByIds(orderItemToCreate.books());
            verifyOrderItemExistsById(orderItemId);
            OrderItem orderItemToSave = OrderItemConverter.fromCreateDtoToModel(orderItemToCreate, customer, bookList);
            orderItemRepository.save(orderItemToSave);
            orderItemsCreated.add(OrderItemConverter.fromModelToOrderITemGetDto(orderItemToSave));
        }
        return orderItemsCreated;

    }*/

    @Override
    public void deleteOrderItem(Long orderItemId) throws OrderItemNotFoundException {
        Optional<OrderItem> orderItemOptional = verifyOrderItemExistsById(orderItemId);
        orderItemRepository.delete(orderItemOptional.get());
    }

    public OrderItem findById(Long orderItemId) throws OrderItemAlreadyExistsException {
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(orderItemId);
        if(orderItemOptional.isEmpty()){
            throw new OrderItemAlreadyExistsException(ORDERITEM_WITH_ID + orderItemId + DOESNT_EXIST);
        }
        return orderItemOptional.get();
    }
}
