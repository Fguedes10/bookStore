package mindera.backendProject.bookStore.dto.order;

import java.time.LocalDate;

public record OrderGetByCustomerDto(
        Long id,
        LocalDate purchaseDate
) {
}
