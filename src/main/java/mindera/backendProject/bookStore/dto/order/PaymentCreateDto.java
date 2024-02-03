package mindera.backendProject.bookStore.dto.order;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import static mindera.backendProject.bookStore.util.Messages.*;

public record PaymentCreateDto(
        @NotNull(message = INVALID_ORDERITEM_ID)
        Long orderItemId,
        @NotNull(message = INVALID_DATE)
        LocalDate paymentDate,
        @NotNull(message = INVALID_AMOUNT)
        double amount,
        boolean isSuccessful
) {

}
