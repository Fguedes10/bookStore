package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.dto.order.OrderItemCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderItemGetDto;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderItemAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.OrderItemNotFoundException;
import mindera.backendProject.bookStore.model.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItemGetDto> getOrderItems();

    OrderItemGetDto getOrderItem(Long orderItemId) throws OrderItemNotFoundException;

    //OrderItemGetDto createOrderItem(OrderItemCreateDto orderItemCreateDto, Long orderItemId) throws CustomerNotFoundException, OrderItemAlreadyExistsException;

    //List<OrderItemGetDto> createOrderItems(List<OrderItemCreateDto> orderItemsCreateDto, Long orderItemId) throws CustomerNotFoundException, OrderItemNotFoundException;

    void deleteOrderItem(Long orderItemId) throws OrderItemNotFoundException;
}
