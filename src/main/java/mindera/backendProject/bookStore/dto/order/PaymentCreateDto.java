package mindera.backendProject.bookStore.dto.order;

import java.time.LocalDate;

public record PaymentCreateDto(

        Long orderItemId,
        LocalDate paymentDate,
        double amount,
        boolean isSuccessful
) {

}
