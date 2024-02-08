package mindera.backendProject.bookStore.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static mindera.backendProject.bookStore.util.Messages.*;

public record AuthorCreateDto(

        @NotBlank(message = INVALID_NAME_AUTHOR)
        @Size(max = 50, message = MAX_CHAR_SIZE)
        @Pattern(regexp = "[a-zA-Z .-]+",message = INVALID_NAME_AUTHOR)
        String name

) {
}
