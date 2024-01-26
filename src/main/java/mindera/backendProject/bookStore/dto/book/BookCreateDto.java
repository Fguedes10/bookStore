package mindera.backendProject.bookStore.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;

public record BookCreateDto(

    @NotBlank(message = INVALID_TITLE)
    @Size(max= 100, message = MAX_CHAR_SIZE)
    String title,

    @NotNull(message = INVALID_ISBN)
    @Size(min = 100000000, max = 999999999, message = INVALID_ISBN)
    Long isbn,

    @NotNull(message = INVALID_AUTHOR_ID)
    Long authorId,
    @NotNull(message = INVALID_PUBLISHER_ID)
    Long publisherId,
    @NotNull(message = INVALID_GENRE_ID)
    List<Long> genre,

    @NotNull(message = INVALID_TRANSLATION_ID)
    List<Long> translation,
    @NotNull(message = INVALID_EDITION)
    @Size(min = 1, max = 9999 , message = MAX_CHAR_SIZE)
    int edition,

    @NotNull(message = INVALID_DATE)
    @Size(min = 1, max = 2024, message = INVALID_DATE)
    int yearRelease,

    @NotNull(message = INVALID_PRICE)
    @Size(min = 1, max = 99999, message = MAX_CHAR_SIZE)
    double price

    ){

}
