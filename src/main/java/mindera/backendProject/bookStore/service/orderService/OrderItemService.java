package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.dto.order.InvoiceCreateDto;
import mindera.backendProject.bookStore.dto.order.InvoiceGetDto;
import mindera.backendProject.bookStore.model.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> getAll();

    OrderItem getOrderItem(Long orderItemId);

    OrderItem createOrderItem(OrderItem orderItem);

    List<OrderItem> createOrderItems(List<OrderItem> orderItems);

    void deleteOrderItem(Long orderItemId);
}
