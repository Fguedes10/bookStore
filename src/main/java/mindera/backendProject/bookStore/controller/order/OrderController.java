package mindera.backendProject.bookStore.controller.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderGetDto;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.InvoiceNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import mindera.backendProject.bookStore.model.OrderModel;
import mindera.backendProject.bookStore.service.orderService.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order", description = "Order endpoints")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService){
        this.orderService = orderService;
    }


    @Operation(
            summary = "Get all existing orders",
            description = "Get all orders"
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get all orders")})
    @GetMapping("/")
    public ResponseEntity<List<OrderGetDto>> getOrders(){
        return ResponseEntity.ok(orderService.getOrders());
    }



    @Operation(
            summary = "Get order by id",
            description = "Get order by id"
    )

    @GetMapping("/id/{orderId}")
    public ResponseEntity<OrderGetDto> getOrder(@PathVariable("orderId")@Parameter(name = "Order Id", description = "Order id",
            example = "1" ) Long orderId) throws OrderNotFoundException {
        return new ResponseEntity<>(orderService.getOrder(orderId), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new order",
            description = "Add new order"
    )
    @PostMapping("/")
    public ResponseEntity<OrderGetDto> addNewOrder(@Valid @RequestBody OrderCreateDto order, Long orderId) throws OrderAlreadyExistsException, CustomerNotFoundException, InvoiceNotFoundException, BookNotFoundException {
        return new ResponseEntity<>(orderService.createOrder(order, orderId), HttpStatus.CREATED);
    }


   @Operation(
            summary = "Add a list of new orders",
            description = "Add a list of new orders"
    )
   @PostMapping("/addMultipleOrders")
    public ResponseEntity<List<OrderGetDto>> addNewOrders(@Valid @RequestBody List<OrderCreateDto> orderCreateDto, Long orderId) throws OrderAlreadyExistsException, InvoiceNotFoundException, CustomerNotFoundException, BookNotFoundException, OrderNotFoundException {
        return new ResponseEntity<>(orderService.createOrders(orderCreateDto, orderId), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Delete order",
            description = "Delete order"
    )
    @DeleteMapping("/id/{orderId}")
    public ResponseEntity<OrderModel> deleteOrderById(@PathVariable ("orderId")@Parameter(name = "Order Id",
            description =  "Order id", example = "1") Long orderId) throws OrderNotFoundException {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
