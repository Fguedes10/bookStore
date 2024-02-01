package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.converter.order.OrderItemConverter;
import mindera.backendProject.bookStore.dto.order.OrderItemGetDto;
import mindera.backendProject.bookStore.exception.order.OrderItemNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import mindera.backendProject.bookStore.model.OrderItem;
import mindera.backendProject.bookStore.model.OrderModel;
import mindera.backendProject.bookStore.repository.orderRepository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.DOESNT_EXIST;
import static mindera.backendProject.bookStore.util.Messages.ORDERMODEL_WITH_ID;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderItemGetDto> getOrderItems() {
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        return orderItemList.stream().map(OrderItemConverter::fromModelToOrderITemGetDto).toList();
    }

    @Override
    public OrderItemGetDto getOrderItem(Long orderItemId) {
        Optional<OrderItem> orderItemOptional = verifyOrderItemExistsById(orderItemId);
        return OrderItemConverter.fromModelToOrderITemGetDto(orderItemOptional.get());
    }

    private Optional<OrderItem> verifyOrderItemExistsById(Long orderItemId) {
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(orderItemId);
        if (orderItemOptional.isEmpty()) {
            throw new OrderItemNotFoundException(ORDERITEM_WITH_ID + orderItemlId + DOESNT_EXIST);
        }
        return orderItemOptional;
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return null;
    }

    @Override
    public List<OrderItem> createOrderItems(List<OrderItem> orderItems) {
        return null;
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {

    }

    public OrderItem findById(Long orderId) {
        return null;
    }
}
