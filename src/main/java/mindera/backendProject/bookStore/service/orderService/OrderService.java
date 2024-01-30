package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderGetDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderCreateDto getOrder(Long orderId);

    OrderGetDto createOrder(OrderCreateDto order);

    List<OrderGetDto> createOrders(List<OrderCreateDto> order);

    void deleteOrder(Long orderId);

    List<OrderCreateDto> getAll();
}
