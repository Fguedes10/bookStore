package mindera.backendProject.bookStore.dto.order;


import mindera.backendProject.bookStore.model.*;

import java.time.LocalDate;
import java.util.List;

public record OrderCreateDto (
        Long customerId,
        List<Long> books,
        LocalDate purchaseDate
) {
}
