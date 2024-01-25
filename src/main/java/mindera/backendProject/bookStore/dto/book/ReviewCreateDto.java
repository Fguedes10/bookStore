package mindera.backendProject.bookStore.dto.book;

import jakarta.persistence.Column;

import java.time.LocalDate;

public record ReviewCreateDto(
        String comment,
        LocalDate commentDate
) {
}
