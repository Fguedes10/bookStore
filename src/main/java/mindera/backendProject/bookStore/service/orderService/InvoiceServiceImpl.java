package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements OrderItemService{
    @Override
    public List<OrderItem> getAll() {
        return null;
    }

    @Override
    public OrderItem getOrderItem(Long orderItemId) {
        return null;
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {

    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return null;
    }

    @Override
    public List<OrderItem> createOrderItems(List<OrderItem> orderItems) {
        return null;
    }
}
