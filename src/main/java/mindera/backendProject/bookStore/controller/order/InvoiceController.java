package mindera.backendProject.bookStore.controller.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mindera.backendProject.bookStore.dto.order.InvoiceGetByCustomerDto;
import mindera.backendProject.bookStore.dto.order.InvoiceGetDto;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.InvoiceNotFoundException;
import mindera.backendProject.bookStore.model.Invoice;
import mindera.backendProject.bookStore.service.orderService.InvoiceServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Invoice", description = "Invoice endpoints")
@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {


    private final InvoiceServiceImpl invoiceService;

    public InvoiceController(InvoiceServiceImpl invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Operation(
            summary = "Get all existing invoices",
            description = "Get all invoices"
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get all invoices")})
    @GetMapping("/")
    public ResponseEntity<List<InvoiceGetDto>> getInvoices() {
        return ResponseEntity.ok(invoiceService.getInvoices());
    }


    @Operation(
            summary = "Get invoice by id",
            description = "Get invoice by id"
    )
    @GetMapping("/id/{invoiceId}")
    public ResponseEntity<InvoiceGetDto> getInvoice(@PathVariable("invoiceId") @Parameter(name = "Invoice Id", description = "Invoice id", example = "1") Long invoiceId) throws InvoiceNotFoundException {
        return new ResponseEntity<>(invoiceService.getInvoice(invoiceId), HttpStatus.OK);
    }


    @Operation(
            summary = "Get all customer invoices",
            description = "Get all customer invoices"
    )
    @GetMapping("/invoiceByCustomer/{customerId}")
    public ResponseEntity<List<InvoiceGetByCustomerDto>> getInvoiceByCustomer(@PathVariable("customerId") @Parameter(name = "Customer Id", description = "Customer id", example = "1") Long customerId) throws InvoiceNotFoundException, CustomerNotFoundException {
        return new ResponseEntity<>(invoiceService.getInvoiceByCustomer(customerId), HttpStatus.OK);
    }


    @Operation(
            summary = "Delete invoice",
            description = "Delete invoice"
    )
    @DeleteMapping("/id/{invoiceId}")
    public ResponseEntity<Invoice> deleteInvoiceById(@PathVariable("invoiceId") @Parameter(name = "Invoice Id",
            description = "Invoice id", example = "1") Long invoiceId) throws InvoiceNotFoundException {
        invoiceService.deleteInvoice(invoiceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
