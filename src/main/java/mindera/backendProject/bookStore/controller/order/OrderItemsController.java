package mindera.backendProject.bookStore.controller.order;

import mindera.backendProject.bookStore.service.orderService.OrderItemServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderItemsController {

    private final OrderItemServiceImpl bookOrderService;

    public OrderItemsController(OrderItemServiceImpl bookOrderService){
        this.bookOrderService = bookOrderService;
    }




}
