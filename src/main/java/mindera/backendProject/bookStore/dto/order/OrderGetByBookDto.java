package mindera.backendProject.bookStore.dto.order;

import java.time.LocalDate;

public record OrderGetByBookDto(
        Long id,
        LocalDate purchaseDate
) {
}
