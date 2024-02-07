package mindera.backendProject.bookStore.controller.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.order.PaymentCreateDto;
import mindera.backendProject.bookStore.dto.order.PaymentGetDto;
import mindera.backendProject.bookStore.exception.order.OrderItemAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.PaymentNotFoundException;
import mindera.backendProject.bookStore.model.Payment;
import mindera.backendProject.bookStore.service.orderService.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;


@Tag(name = PAYMENT_TAG_NAME , description = PAYMENT_TAG_DESCRIPTION)
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentServiceImpl paymentService;

    @Operation(
            summary = GET_ALL_EXIST_PAYMENTS,
            description = GET_ALL_EXIST_PAYMENTS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = PAYMENTS_FOUND)})
    @GetMapping("/")
    public ResponseEntity<List<PaymentGetDto>> getPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }


    @Operation(
            summary = GET_PAYMENT_BY_ID,
            description = GET_PAYMENT_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = PAYMENT_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = PAYMENT_NOT_FOUND)})
    @GetMapping("/id/{paymentId}")
    public ResponseEntity<PaymentGetDto> getPayment(@PathVariable("paymentId") @Parameter(name = "Payment Id", description = "Payment id", example = "1") Long paymentId) throws PaymentNotFoundException {
        return new ResponseEntity<>(paymentService.getPayment(paymentId), HttpStatus.OK);
    }

    @Operation(
            summary = ADD_NEW_PAYMENT,
            description = ADD_NEW_PAYMENT
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = PAYMENT_CREATED),
            @ApiResponse(responseCode = CONFLICT, description = PAYMENT_ALREADY_EXISTS)
    })
    @PostMapping("/")
    public ResponseEntity<PaymentGetDto> addNewPayment(@Valid @RequestBody PaymentCreateDto payment, Long paymentId) throws OrderItemAlreadyExistsException {
        return new ResponseEntity<>(paymentService.createPayment(payment, paymentId), HttpStatus.CREATED);
    }


    @Operation(
            summary = DELETE_PAYMENT_BY_ID,
            description = DELETE_PAYMENT_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = PAYMENT_DELETED),
            @ApiResponse(responseCode = NOT_FOUND, description = PAYMENT_NOT_FOUND)
    })
    @DeleteMapping("/id/{paymentId}")
    public ResponseEntity<Payment> deletePaymentById(@PathVariable("paymentId") @Parameter(name = "Payment Id",
            description = "Payment id", example = "1") Long paymentId) throws PaymentNotFoundException {
        paymentService.deletePayment(paymentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
