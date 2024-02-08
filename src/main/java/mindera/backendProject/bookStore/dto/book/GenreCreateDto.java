package mindera.backendProject.bookStore.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static mindera.backendProject.bookStore.util.Messages.*;

public record GenreCreateDto(
        @NotBlank(message = INVALID_BOOK_GENRE)
        @Size(max = 30, message = MAX_CHAR_SIZE)
        @Pattern(regexp = "[a-zA-Z ]+",message = INVALID_BOOK_GENRE)
        String name
) {
}
