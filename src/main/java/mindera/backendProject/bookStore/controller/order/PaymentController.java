package mindera.backendProject.bookStore.controller.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.exception.order.PaymentAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.PaymentNotFoundException;
import mindera.backendProject.bookStore.model.Payment;
import mindera.backendProject.bookStore.service.orderService.PaymentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Payment", description = "Payment endpoints")
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {


    private final PaymentServiceImpl paymentService;

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(
            summary = "Get all existing payments",
            description = "Get all payments"
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get all payments")})
    @GetMapping("/")
    public ResponseEntity<List<Payment>> getPayments(){
        return ResponseEntity.ok(paymentService.getAll());
    }



    @Operation(
            summary = "Get payment by id",
            description = "Get payment by id"
    )
    @GetMapping("/id/{paymentId}")
    public ResponseEntity<Payment> getPayment(@PathVariable("paymentId")@Parameter(name = "Payment Id", description = "Payment id", example = "1" ) Long paymentId) throws PaymentNotFoundException {
        return new ResponseEntity<>(paymentService.getPayment(paymentId), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new payment",
            description = "Add new payment"
    )
    @PostMapping("/")
    public ResponseEntity<Payment> addNewPayment(@Valid @RequestBody Payment payment) throws PaymentAlreadyExistsException {
        return new ResponseEntity<>(paymentService.createPayment(payment), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Add a list of new payments",
            description = "Add a list of new payments"
    )
    @PostMapping("/addMultiplePayments")
    public ResponseEntity<List<Payment>> addNewPayments(@Valid @RequestBody List<Payment> payment) throws PaymentAlreadyExistsException{
        return new ResponseEntity<>(paymentService.createPayments(payment), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Delete payment",
            description = "Delete payment"
    )
    @DeleteMapping("/id/{paymentId}")
    public ResponseEntity<Payment> deletePaymentById(@PathVariable ("paymentId")@Parameter(name = "Payment Id",
            description =  "Payment id", example = "1") Long paymentId) throws PaymentNotFoundException {
        paymentService.deletePayment(paymentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
