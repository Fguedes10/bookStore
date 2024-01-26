package mindera.backendProject.bookStore.dto.book;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;
import java.time.Year;

import static mindera.backendProject.bookStore.util.Messages.*;
import static mindera.backendProject.bookStore.util.Messages.INVALID_DATE;

public record ReviewCreateDto(

        @NotBlank(message = INVALID_BOOK_REVIEW)
        @Pattern(regexp = "[a-zA-Z_0-9!?#$]+",message = INVALID_BOOK_REVIEW)
        String comment


) {
}
