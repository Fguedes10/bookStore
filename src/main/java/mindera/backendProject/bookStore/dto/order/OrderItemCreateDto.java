package mindera.backendProject.bookStore.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import mindera.backendProject.bookStore.model.Book;

import java.util.List;
import java.util.Set;

import static mindera.backendProject.bookStore.util.Messages.INVALID_AMOUNT;
import static mindera.backendProject.bookStore.util.Messages.INVALID_CUSTOMER_ID;

public record OrderItemCreateDto(
       @NotNull(message = INVALID_CUSTOMER_ID)
        Long customerId,

       List<Long> books,
       @NotNull(message = INVALID_AMOUNT)
       double amountToPay,
       boolean isOrdered
) {





}
