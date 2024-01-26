package mindera.backendProject.bookStore.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static mindera.backendProject.bookStore.util.Messages.*;

public record TranslationCreateDto(

        @NotBlank(message = INVALID_BOOK_TRANSLATION)
        @Size(max = 15, message = MAX_CHAR_SIZE)
        @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ]+$",message = INVALID_BOOK_TRANSLATION)
        String name
) {
}
