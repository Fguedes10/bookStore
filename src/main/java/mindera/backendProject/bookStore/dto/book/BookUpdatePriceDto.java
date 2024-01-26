package mindera.backendProject.bookStore.dto.book;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static mindera.backendProject.bookStore.util.Messages.INVALID_PRICE;
import static mindera.backendProject.bookStore.util.Messages.MAX_CHAR_SIZE;

public record BookUpdatePriceDto (
        @NotNull(message = INVALID_PRICE)
        @Size(min = 1, max = 99999, message = MAX_CHAR_SIZE)
        double price
){
}
