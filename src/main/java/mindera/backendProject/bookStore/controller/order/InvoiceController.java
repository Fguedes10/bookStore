package mindera.backendProject.bookStore.controller.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.order.InvoiceCreateDto;
import mindera.backendProject.bookStore.dto.order.InvoiceGetDto;
import mindera.backendProject.bookStore.exception.order.InvoiceAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.InvoiceNotFoundException;
import mindera.backendProject.bookStore.model.Invoice;
import mindera.backendProject.bookStore.model.OrderItem;
import mindera.backendProject.bookStore.service.orderService.InvoiceServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Invoice", description = "Invoice endpoints")
@RestController
@RequestMapping("/api/v1/Controller")
public class InvoiceController {

    private final InvoiceServiceImpl invoiceService;

    public InvoiceController(InvoiceServiceImpl invoiceService){
        this.invoiceService = invoiceService;
    }


    @Operation(
            summary = "Get all existing invoices",
            description = "Get all invoices"
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get all invoices")})
    @GetMapping("/")
    public ResponseEntity<List<OrderItem>> getInvoices(){
        return ResponseEntity.ok(invoiceService.getAll());
    }



    @Operation(
            summary = "Get invoice by id",
            description = "Get invoice by id"
    )
    @GetMapping("/id/{invoiceId}")
    public ResponseEntity<InvoiceCreateDto> getInvoice(@PathVariable("invoiceId")@Parameter(name = "Invoice Id", description = "Invoice id", example = "1" ) Long invoiceId) throws InvoiceNotFoundException {
        return new ResponseEntity<>(invoiceService.getInvoice(invoiceId), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new invoice",
            description = "Add new invoice"
    )
    @PostMapping("/")
    public ResponseEntity<InvoiceGetDto> addNewInvoice(@Valid @RequestBody InvoiceCreateDto invoice) throws InvoiceAlreadyExistsException {
        return new ResponseEntity<>(invoiceService.createInvoice(invoice), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Add a list of new invoices",
            description = "Add a list of new invoices"
    )
    @PostMapping("/addMultipleInvoices")
    public ResponseEntity<List<InvoiceGetDto>> addNewInvoices(@Valid @RequestBody List<InvoiceCreateDto> invoice) throws InvoiceAlreadyExistsException{
        return new ResponseEntity<>(invoiceService.createInvoices(invoice), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Delete invoice",
            description = "Delete invoice"
    )
    @DeleteMapping("/id/{invoiceId}")
    public ResponseEntity<Invoice> deleteInvoiceById(@PathVariable ("invoiceId")@Parameter(name = "Invoice Id",
            description =  "Invoice id", example = "1") Long invoiceId) throws InvoiceNotFoundException {
        invoiceService.deleteInvoice(invoiceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
