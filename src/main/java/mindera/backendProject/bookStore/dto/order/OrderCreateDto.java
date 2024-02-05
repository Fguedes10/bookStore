package mindera.backendProject.bookStore.dto.order;


import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;

public record OrderCreateDto(
        @NotNull(message = INVALID_CUSTOMER_ID)
        Long customerId,
        @NotNull(message = INVALID_BOOK_ID)
        List<Long> books,
        @NotNull(message = INVALID_DATE)
        LocalDate purchaseDate
) {

}
