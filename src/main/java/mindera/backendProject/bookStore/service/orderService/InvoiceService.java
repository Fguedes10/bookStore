package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.dto.order.InvoiceCreateDto;
import mindera.backendProject.bookStore.dto.order.InvoiceGetDto;
import mindera.backendProject.bookStore.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvoiceService {
    List<OrderItem> getAll();

    InvoiceCreateDto getInvoice(Long invoiceId);

    InvoiceGetDto createInvoice(InvoiceCreateDto invoice);

    List<InvoiceGetDto> createInvoices(List<InvoiceCreateDto> invoice);

    void deleteInvoice(Long invoiceId);
}
