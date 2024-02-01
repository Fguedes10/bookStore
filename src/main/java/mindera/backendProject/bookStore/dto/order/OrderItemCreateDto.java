package mindera.backendProject.bookStore.dto.order;

import mindera.backendProject.bookStore.model.Book;

import java.util.Set;

public record OrderItemCreateDto(
        Long customerId,
        Set<Book>books,
        double amountToPay,
        boolean isOrdered
) {





}
