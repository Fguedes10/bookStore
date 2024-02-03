package mindera.backendProject.bookStore.dto.order;
import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import java.time.LocalDate;

public record InvoiceGetDto (
        String customerName,
        Long orderModelId,
        LocalDate issueDate,
        double totalAmount,
        int invoiceNumber,
        double VAT
) {


}
