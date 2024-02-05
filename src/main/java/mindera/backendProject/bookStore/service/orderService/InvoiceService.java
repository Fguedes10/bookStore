package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.dto.order.InvoiceGetDto;
import mindera.backendProject.bookStore.exception.order.InvoiceNotFoundException;

import java.util.List;


public interface InvoiceService {
    List<InvoiceGetDto> getInvoices();

    InvoiceGetDto getInvoice(Long invoiceId) throws InvoiceNotFoundException;


    void deleteInvoice(Long invoiceId) throws InvoiceNotFoundException;
}
