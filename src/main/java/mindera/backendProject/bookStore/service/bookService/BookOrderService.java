package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.order.OrderItemsCreateDto;

import java.util.List;

public interface BookOrderService {
    List<OrderItemsCreateDto> getAll();

    OrderItemsCreateDto getBookORder(Long bookOrderId);
}
