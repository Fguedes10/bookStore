package mindera.backendProject.bookStore.controller.order;

import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.order.OrderGetByBookDto;
import mindera.backendProject.bookStore.dto.order.OrderGetByCustomerDto;
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

import java.io.FileNotFoundException;
import java.util.List;

@Tag(name = "Order", description = "Order endpoints")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }


    @Operation(
            summary = "Get all existing orders",
            description = "Get all orders"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all orders")})
    @GetMapping("/")
    public ResponseEntity<List<OrderGetDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }


    @Operation(
            summary = "Get order by id",
            description = "Get order by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get order"),
            @ApiResponse(responseCode = "404", description = "Order not found")})
    @GetMapping("/id/{orderId}")
    public ResponseEntity<OrderGetDto> getOrder(@PathVariable("orderId") @Parameter(name = "Order Id", description = "Order id",
            example = "1") Long orderId) throws OrderNotFoundException {
        return new ResponseEntity<>(orderService.getOrder(orderId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get order by customer",
            description = "Get order by customer"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get order"),
            @ApiResponse(responseCode = "404", description = "Order not found or Customer not found")
    })
    @GetMapping("/orderByCustomer/{customerId}")
    public ResponseEntity<List<OrderGetByCustomerDto>> getOrderByCustomer(@PathVariable("customerId") @Parameter(name = "Customer Id", description = "Customer id",
            example = "1") Long customerId) throws OrderNotFoundException, CustomerNotFoundException {
        return new ResponseEntity<>(orderService.getOrderByCostumer(customerId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get order by book",
            description = "Get order by book"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get order"),
            @ApiResponse(responseCode = "404", description = "Order not found or Book not found")
    })
    @GetMapping("/orderByBook/{bookId}")
    public ResponseEntity<List<OrderGetByBookDto>> getOrderByBook(@PathVariable("bookId") @Parameter(name = "Book Id", description = "Book id",
            example = "1") Long bookId) throws OrderNotFoundException, BookNotFoundException {
        return new ResponseEntity<>(orderService.getOrderByBook(bookId), HttpStatus.OK);
    }



    @Operation(
            summary = "Delete order",
            description = "Delete order"
    )
    @ApiResponses(value = {

    })
    @DeleteMapping("/id/{orderId}")
    public ResponseEntity<OrderModel> deleteOrderById(@PathVariable("orderId") @Parameter(name = "Order Id",
            description = "Order id", example = "1") Long orderId) throws OrderNotFoundException {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Add new order",
            description = "Add new order"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created"),
            @ApiResponse(responseCode = "400", description = "Document failed to load"),
            @ApiResponse(responseCode = "404", description = "Customer or File not found"),
            @ApiResponse(responseCode = "409", description = "Order already exists")
    })
    @PostMapping("/")
    public ResponseEntity<OrderGetDto> addNewOrder(@Valid @RequestBody OrderModel order, Long orderId) throws OrderAlreadyExistsException, CustomerNotFoundException, DocumentException, FileNotFoundException {
        return new ResponseEntity<>(orderService.createOrder(order, orderId), HttpStatus.CREATED);
    }

}
