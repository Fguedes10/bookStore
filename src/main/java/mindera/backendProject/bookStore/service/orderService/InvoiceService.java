package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.dto.order.InvoiceCreateDto;
import mindera.backendProject.bookStore.dto.order.InvoiceGetDto;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.order.InvoiceAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.InvoiceNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InvoiceService {
    List<InvoiceGetDto> getInvoices();

    InvoiceGetDto getInvoice(Long invoiceId) throws InvoiceNotFoundException;

    InvoiceGetDto createInvoice(InvoiceCreateDto invoice, int invoiceNumber) throws InvoiceAlreadyExistsException, CustomerNotFoundException, OrderNotFoundException;

   List<InvoiceGetDto> createInvoices(List<InvoiceCreateDto> invoice, int invoiceNumber) throws CustomerNotFoundException, OrderNotFoundException, InvoiceNotFoundException, InvoiceAlreadyExistsException;

    void deleteInvoice(Long invoiceId) throws InvoiceNotFoundException;
}
