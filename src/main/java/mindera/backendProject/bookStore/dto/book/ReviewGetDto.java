package mindera.backendProject.bookStore.dto.book;

import java.time.LocalDate;

public record ReviewGetDto(
        Long bookId,

        String bookTitle,

        String comment,

        LocalDate commentDate
) {
}
