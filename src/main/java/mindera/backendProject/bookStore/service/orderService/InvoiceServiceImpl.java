package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.dto.order.InvoiceCreateDto;
import mindera.backendProject.bookStore.dto.order.InvoiceGetDto;
import mindera.backendProject.bookStore.exception.book.AuthorNotFoundException;
import mindera.backendProject.bookStore.exception.order.InvoiceNotFoundException;
import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.model.Invoice;
import mindera.backendProject.bookStore.model.OrderItem;
import mindera.backendProject.bookStore.repository.orderRepository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

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

    public Invoice findById(Long invoiceId) throws InvoiceNotFoundException {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);
        if(invoiceOptional.isEmpty()){
            throw new InvoiceNotFoundException(INVOICE_WITH_ID + invoiceId + DOESNT_EXIST);
        }
        return invoiceOptional.get();
    }

}
