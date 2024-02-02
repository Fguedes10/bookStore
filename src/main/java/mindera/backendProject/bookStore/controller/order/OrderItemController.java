package mindera.backendProject.bookStore.controller.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.order.OrderItemCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderItemGetDto;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderItemAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.OrderItemNotFoundException;
import mindera.backendProject.bookStore.model.OrderItem;
import mindera.backendProject.bookStore.service.orderService.OrderItemServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "OrderItem", description = "OrderItem endpoints")
@RestController
@RequestMapping("/api/v1/orderItems")
public class OrderItemController {

    private final OrderItemServiceImpl orderItemService;

    public OrderItemController(OrderItemServiceImpl orderItemService) {
        this.orderItemService = orderItemService;
    }


    @Operation(
            summary = "Get all existing orderItems",
            description = "Get all orderItems"
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get all orderItems")})
    @GetMapping("/")
    public ResponseEntity<List<OrderItemGetDto>> getOrderItems() {
        return ResponseEntity.ok(orderItemService.getOrderItems());
    }


    @Operation(
            summary = "Get orderItem by id",
            description = "Get orderItem by id"
    )
    @GetMapping("/id/{orderItemId}")
    public ResponseEntity<OrderItemGetDto> getOrderItem(@PathVariable("orderItemId") @Parameter(name = "OrderItem Id", description = "OrderItem id", example = "1") Long orderItemId) throws OrderItemNotFoundException {
        return new ResponseEntity<>(orderItemService.getOrderItem(orderItemId), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new orderItem",
            description = "Add new orderItem"
    )
    @PostMapping("/")
    public ResponseEntity<OrderItemGetDto> addNewOrderItem(@Valid @RequestBody OrderItemCreateDto orderItemCreateDto, Long orderItemId) throws OrderItemAlreadyExistsException, CustomerNotFoundException, BookNotFoundException {
        return new ResponseEntity<>(orderItemService.createOrderItem(orderItemCreateDto, orderItemId), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Add a list of new orderItems",
            description = "Add a list of new orderItems"
    )
    @PostMapping("/addMany")
    public ResponseEntity<List<OrderItemGetDto>> addNewOrderItems(@Valid @RequestBody List<OrderItemCreateDto> orderItemCreateDto, Long orderItemId) throws OrderItemNotFoundException, CustomerNotFoundException, BookNotFoundException {
        return new ResponseEntity<>(orderItemService.createOrderItems(orderItemCreateDto, orderItemId), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete orderItem by id",
            description = "Delete orderItem by id"
    )
    @DeleteMapping("/id/{orderItemId}")
    public ResponseEntity<OrderItem> delete(@PathVariable("orderItemId") @Parameter(name = "OrderItem Id",
            description = "OrderItem id", example = "1") Long orderItemId) throws OrderItemNotFoundException {
        orderItemService.deleteOrderItem(orderItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
