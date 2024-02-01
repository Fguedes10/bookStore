package mindera.backendProject.bookStore.dto.order;

import mindera.backendProject.bookStore.dto.book.BookGetDto;

import java.util.Set;

public record OrderItemGetDto(
        String customerName,
        Set<BookGetDto> books,
        double amountToPay,
        boolean isOrdered
) {
}

