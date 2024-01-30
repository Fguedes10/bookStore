package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderGetDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Override
    public OrderCreateDto getOrder(Long orderId) {
        return null;
    }

    @Override
    public OrderGetDto createOrder(OrderCreateDto order) {
        return null;
    }

    @Override
    public List<OrderGetDto> createOrders(List<OrderCreateDto> order) {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) {

    }

    @Override
    public List<OrderCreateDto> getAll() {
        return null;
    }
}
