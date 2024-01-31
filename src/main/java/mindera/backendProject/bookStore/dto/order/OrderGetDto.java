package mindera.backendProject.bookStore.dto.order;

import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.dto.customer.CustomerGetDto;

import java.time.LocalDate;
import java.util.List;

public record OrderGetDto(
        CustomerGetDto customer,
        List<BookGetDto> book,
        LocalDate purchaseDate
) {
}
