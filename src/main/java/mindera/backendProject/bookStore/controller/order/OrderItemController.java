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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;

@Tag(name = ORDERITEM_TAG_NAME, description = ORDERITEM_TAG_DESCRIPTION)
@RestController
@RequestMapping("/api/v1/orderItems")
public class OrderItemController {

    @Autowired
    private OrderItemServiceImpl orderItemService;

    @Operation(
            summary = GET_ALL_EXIST_ORDERITEMS,
            description = GET_ALL_EXIST_ORDERITEMS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = ORDERITEMS_FOUND)})
    @GetMapping("/")
    public ResponseEntity<List<OrderItemGetDto>> getOrderItems() {
        return ResponseEntity.ok(orderItemService.getOrderItems());
    }


    @Operation(
            summary = GET_ORDERITEM_BY_ID,
            description = GET_ORDERITEM_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = ORDERITEM_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = ORDERITEM_NOT_FOUND)})
    @GetMapping("/id/{orderItemId}")
    public ResponseEntity<OrderItemGetDto> getOrderItem(@PathVariable("orderItemId") @Parameter(name = "OrderItem Id", description = "OrderItem id", example = "1") Long orderItemId) throws OrderItemNotFoundException {
        return new ResponseEntity<>(orderItemService.getOrderItem(orderItemId), HttpStatus.OK);
    }

    @Operation(
            summary = ADD_ORDERITEM,
            description = ADD_ORDERITEM
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = ORDERITEM_CREATED),
            @ApiResponse(responseCode = NOT_FOUND, description = CUSTOMER_OR_BOOK_NOT_FOUND),
            @ApiResponse(responseCode = CONFLICT, description = ORDERITEM_ALREADY_EXISTS)
    })
    @PostMapping("/{customerId}")
    public ResponseEntity<OrderItemGetDto> addNewOrderItem(@PathVariable ("customerId") @Parameter(name = "Customer Id", description = "Customer id", example = "1") Long customerId, @Valid @RequestBody OrderItemCreateDto orderItemCreateDto) throws CustomerNotFoundException, BookNotFoundException {
        return new ResponseEntity<>(orderItemService.createOrderItem(orderItemCreateDto, customerId), HttpStatus.CREATED);
    }


    @Operation(
            summary = ADD_MULTIPLE_ORDERITEMS,
            description = ADD_MULTIPLE_ORDERITEMS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = ORDERITEM_CREATED),
            @ApiResponse(responseCode = NOT_FOUND, description = CUSTOMER_OR_BOOK_NOT_FOUND),
            @ApiResponse(responseCode = CONFLICT, description = ORDERITEM_ALREADY_EXISTS)
    })
    @PostMapping("/addMany")
    public ResponseEntity<List<OrderItemGetDto>> addNewOrderItems(@Valid @RequestBody List<OrderItemCreateDto> orderItemCreateDto, Long orderItemId) throws OrderItemNotFoundException, CustomerNotFoundException, BookNotFoundException {
        return new ResponseEntity<>(orderItemService.createOrderItems(orderItemCreateDto, orderItemId), HttpStatus.CREATED);
    }

    @Operation(
            summary = DELETE_ORDERITEM_BY_ID,
            description = DELETE_ORDERITEM_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = ORDERITEM_DELETED),
            @ApiResponse(responseCode = NOT_FOUND, description = ORDERITEM_NOT_FOUND)
    })
    @DeleteMapping("/id/{orderItemId}")
    public ResponseEntity<OrderItem> delete(@PathVariable("orderItemId") @Parameter(name = "OrderItem Id",
            description = "OrderItem id", example = "1") Long orderItemId) throws OrderItemNotFoundException {
        orderItemService.deleteOrderItem(orderItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
