package mindera.backendProject.bookStore.dto.book;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static mindera.backendProject.bookStore.util.Messages.INVALID_EDITION;
import static mindera.backendProject.bookStore.util.Messages.MAX_CHAR_SIZE;

public record BookUpdateEditionDto (
        @NotNull(message = INVALID_EDITION)
        @Size(min = 1, max = 9999 , message = MAX_CHAR_SIZE)
        int edition
){

}
