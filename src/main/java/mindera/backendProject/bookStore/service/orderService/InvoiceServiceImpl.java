package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.dto.order.InvoiceCreateDto;
import mindera.backendProject.bookStore.dto.order.InvoiceGetDto;
import mindera.backendProject.bookStore.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Override
    public List<OrderItem> getAll() {
        return null;
    }

    @Override
    public InvoiceCreateDto getInvoice(Long invoiceId) {
        return null;
    }

    @Override
    public InvoiceGetDto createInvoice(InvoiceCreateDto invoice) {
        return null;
    }

    @Override
    public List<InvoiceGetDto> createInvoices(List<InvoiceCreateDto> invoice) {
        return null;
    }

    @Override
    public void deleteInvoice(Long invoiceId) {

    }
}
