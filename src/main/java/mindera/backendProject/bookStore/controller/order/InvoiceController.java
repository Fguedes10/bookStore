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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;

@Tag(name = INVOICE_TAG_NAME, description = INVOICE_TAG_DESCRIPTION)
@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceServiceImpl invoiceService;

    @Operation(
            summary = GET_ALL_EXIST_INVOICES,
            description = GET_ALL_EXIST_INVOICES
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = INVOICES_FOUND)})
    @GetMapping("/")
    public ResponseEntity<List<InvoiceGetDto>> getInvoices() {
        return ResponseEntity.ok(invoiceService.getInvoices());
    }


    @Operation(
            summary = GET_INVOICE_BY_ID,
            description = GET_INVOICE_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = INVOICE_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = INVOICE_NOT_FOUND)})
    @GetMapping("/id/{invoiceId}")
    public ResponseEntity<InvoiceGetDto> getInvoice(@PathVariable("invoiceId") @Parameter(name = "Invoice Id", description = "Invoice id", example = "1") Long invoiceId) throws InvoiceNotFoundException {
        return new ResponseEntity<>(invoiceService.getInvoice(invoiceId), HttpStatus.OK);
    }


    @Operation(
            summary = GET_INVOICE_BY_CUSTOMER_BY_ID,
            description = GET_INVOICE_BY_CUSTOMER_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = INVOICES_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = CUSTOMER_NOT_FOUND)})
    @GetMapping("/invoiceByCustomer/{customerId}")
    public ResponseEntity<List<InvoiceGetByCustomerDto>> getInvoiceByCustomer(@PathVariable("customerId") @Parameter(name = "Customer Id", description = "Customer id", example = "1") Long customerId) throws InvoiceNotFoundException, CustomerNotFoundException {
        return new ResponseEntity<>(invoiceService.getInvoiceByCustomer(customerId), HttpStatus.OK);
    }


    @Operation(
            summary = DELETE_INVOICE_BY_ID,
            description = DELETE_INVOICE_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = INVOICE_DELETED),
            @ApiResponse(responseCode = NOT_FOUND, description = INVOICE_NOT_FOUND)})
    @DeleteMapping("/id/{invoiceId}")
    public ResponseEntity<Invoice> deleteInvoiceById(@PathVariable("invoiceId") @Parameter(name = "Invoice Id",
            description = "Invoice id", example = "1") Long invoiceId) throws InvoiceNotFoundException {
        invoiceService.deleteInvoice(invoiceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
