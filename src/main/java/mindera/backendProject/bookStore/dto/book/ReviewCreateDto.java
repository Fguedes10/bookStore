package mindera.backendProject.bookStore.dto.book;


import java.time.LocalDate;

public record ReviewCreateDto(
        String comment,
        LocalDate commentDate
) {
}
