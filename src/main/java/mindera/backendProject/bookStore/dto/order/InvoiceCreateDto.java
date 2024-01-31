package mindera.backendProject.bookStore.dto.order;

import java.time.LocalDate;

public record InvoiceCreateDto(

        Long customerId,
        Long orderModelId,
        LocalDate issueDate,
        double totalAmount,
        int invoiceNumber,
        double VAT

) {

}
