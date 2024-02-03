package mindera.backendProject.bookStore.dto.order;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import static mindera.backendProject.bookStore.util.Messages.*;

public record InvoiceCreateDto(

        @NotNull(message = INVALID_CUSTOMER_ID)
        Long customerId,
        @NotNull(message = INVALID_ORDERITEM_ID)
        Long orderModelId,
        @NotNull(message = INVALID_DATE)
        LocalDate issueDate,
        @NotNull(message = INVALID_AMOUNT)
        double totalAmount,
       @NotNull(message = INVALID_VAT)
        double VAT

) {

}
