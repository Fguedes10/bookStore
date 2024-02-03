package mindera.backendProject.bookStore.dto.order;

import java.time.LocalDate;

public record PaymentGetDto(
        LocalDate paymentDate,
        double amount,
        boolean isSuccessful
) {
}
