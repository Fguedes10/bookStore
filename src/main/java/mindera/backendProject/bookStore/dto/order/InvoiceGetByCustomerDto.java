package mindera.backendProject.bookStore.dto.order;

import java.time.LocalDate;

public record InvoiceGetByCustomerDto(

        Long id,
        LocalDate issueDate,
        int invoiceNumber,
        double totalAmount
) {
}
