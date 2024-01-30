package mindera.backendProject.bookStore.controller.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    public OrderItemController(OrderItemServiceImpl orderItemService){
        this.orderItemService=orderItemService;
    }



    @Operation(
            summary = "Get all existing orderItems",
            description = "Get all orderItems"
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get all orderItems")})
    @GetMapping("/")
    public ResponseEntity<List<OrderItem>> getOrderItems(){
        return ResponseEntity.ok(orderItemService.getAll());
    }



    @Operation(
            summary = "Get orderItem by id",
            description = "Get orderItem by id"
    )
    @GetMapping("/id/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable("orderItemId")@Parameter(name = "OrderItem Id", description = "OrderItem id", example = "1" ) Long orderItemId) throws OrderItemNotFoundException {
        return new ResponseEntity<>(orderItemService.getOrderItem(orderItemId), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new orderItem",
            description = "Add new orderItem"
    )
    @PostMapping("/")
    public ResponseEntity<OrderItem> addNewOrderItem(@Valid @RequestBody OrderItem orderItem) throws OrderItemAlreadyExistsException {
        return new ResponseEntity<>(orderItemService.createOrderItem(orderItem), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Add a list of new orderItems",
            description = "Add a list of new orderItems"
    )
    @PostMapping("/addMultipleOrderItems")
    public ResponseEntity<List<OrderItem>> addNewOrderItem(@Valid @RequestBody List<OrderItem> orderItems) throws OrderItemAlreadyExistsException{
        return new ResponseEntity<>(orderItemService.createOrderItems(orderItems), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete orderItem",
            description = "Delete orderItem"
    )
    @DeleteMapping("/id/{orderItemId}")
    public ResponseEntity<OrderItem> deleteOrderItemById(@PathVariable ("orderItemId")@Parameter(name = "OrderItem Id",
            description =  "OrderItem id", example = "1") Long orderItemId) throws OrderItemNotFoundException {
        orderItemService.deleteOrderItem(orderItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
