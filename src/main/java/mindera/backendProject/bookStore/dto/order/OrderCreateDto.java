package mindera.backendProject.bookStore.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.model.*;

import java.time.LocalDate;
import java.util.List;

public record OrderCreateDto (
        List<OrderItem> orderItem,
        Long customerId,
        List<Download> download,
        Long invoiceId,
        List<Book> book,
        LocalDate purchaseDate
) {
}
