package mindera.backendProject.bookStore.controller.order;

import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderGetByBookDto;
import mindera.backendProject.bookStore.dto.order.OrderGetByCustomerDto;
import mindera.backendProject.bookStore.dto.order.OrderGetDto;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import mindera.backendProject.bookStore.exception.order.PdfNotFoundException;
import mindera.backendProject.bookStore.model.OrderModel;
import mindera.backendProject.bookStore.service.orderService.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;

@Tag(name = ORDER_TAG_NAME, description = ORDER_TAG_DESCRIPTION)
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }


    @Operation(
            summary = GET_ALL_EXIST_ORDERS,
            description = GET_ALL_EXIST_ORDERS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = ORDERS_FOUND)})
    @GetMapping("/")
    public ResponseEntity<List<OrderGetDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }


    @Operation(
            summary = GET_ORDER_BY_ID,
            description = GET_ORDER_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = ORDER_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = ORDER_NOT_FOUND)})
    @GetMapping("/id/{orderId}")
    public ResponseEntity<OrderGetDto> getOrder(@PathVariable("orderId") @Parameter(name = "Order Id", description = "Order id",
            example = "1") Long orderId) throws OrderNotFoundException {
        return new ResponseEntity<>(orderService.getOrder(orderId), HttpStatus.OK);
    }

    @Operation(
            summary = GET_ORDER_BY_CUSTOMER_ID,
            description = GET_ORDER_BY_CUSTOMER_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = ORDER_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = CUSTOMER_OR_ORDER_NOT_FOUND)
    })
    @GetMapping("/orderByCustomer/{customerId}")
    public ResponseEntity<List<OrderGetByCustomerDto>> getOrderByCustomer(@PathVariable("customerId") @Parameter(name = "Customer Id", description = "Customer id",
            example = "1") Long customerId) throws OrderNotFoundException, CustomerNotFoundException {
        return new ResponseEntity<>(orderService.getOrderByCostumer(customerId), HttpStatus.OK);
    }

    @Operation(
            summary = GET_ORDER_BY_BOOK_ID,
            description = GET_ORDER_BY_BOOK_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = ORDER_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = BOOK_OR_ORDER_NOT_FOUND)
    })
    @GetMapping("/orderByBook/{bookId}")
    public ResponseEntity<List<OrderGetByBookDto>> getOrderByBook(@PathVariable("bookId") @Parameter(name = "Book Id", description = "Book id",
            example = "1") Long bookId) throws OrderNotFoundException, BookNotFoundException {
        return new ResponseEntity<>(orderService.getOrderByBook(bookId), HttpStatus.OK);
    }


    @Operation(
            summary = DELETE_ORDER_BY_ID,
            description = DELETE_ORDER_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = ORDER_DELETED),
            @ApiResponse(responseCode = NOT_FOUND, description = ORDER_NOT_FOUND)
    })
    @DeleteMapping("/id/{orderId}")
    public ResponseEntity<OrderModel> deleteOrderById(@PathVariable("orderId") @Parameter(name = "Order Id",
            description = "Order id", example = "1") Long orderId) throws OrderNotFoundException {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = ADD_ORDER,
            description = ADD_ORDER
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = ORDER_CREATED),
            @ApiResponse(responseCode = DOCUMENT_FAILED, description = DOCUMENT_FAILED_LOAD),
            @ApiResponse(responseCode = NOT_FOUND, description = CUSTOMER_OR_FILE_NOT_FOUND),
            @ApiResponse(responseCode = CONFLICT, description = ORDERMODEL_ALREADY_EXISTS)
    })
    @PostMapping("/")
    public ResponseEntity<OrderGetDto> addNewOrder(@Valid @RequestBody OrderCreateDto order, Long orderId) throws OrderAlreadyExistsException, CustomerNotFoundException, DocumentException, FileNotFoundException, BookNotFoundException, PdfNotFoundException {
        return new ResponseEntity<>(orderService.createOrder(order, orderId), HttpStatus.CREATED);
    }

}
