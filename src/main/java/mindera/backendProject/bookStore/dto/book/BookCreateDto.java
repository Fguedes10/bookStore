package mindera.backendProject.bookStore.dto.book;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import mindera.backendProject.bookStore.model.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static mindera.backendProject.bookStore.util.Messages.*;

public record BookCreateDto(

    @NotBlank(message = INVALID_TITLE)
    @Size(max= 100, message = MAX_CHAR_SIZE)
    String title,

    @NotBlank(message = INVALID_ISBN)
    @Size(min = 100000000, max = 999999999, message = INVALID_ISBN)
    Long isbn,
    Long authorId,
    @NotBlank(message = INVALID_PUBLISHER)
    @Size(max = 20, message = MAX_CHAR_SIZE)
    String publisher,
    List<Long> genre,
    List<Long> translation,
    @NotBlank(message = INVALID_EDITION)
    @Size(min = 1, max = 9999 , message = MAX_CHAR_SIZE)
    int edition,

    @NotBlank(message = INVALID_DATE)
    @Size(min = 1, max = 2024, message = INVALID_DATE)
    LocalDate yearRelease,

    @NotBlank(message = INVALID_PRICE)
    @Size(min = 1, max = 99999, message = MAX_CHAR_SIZE)
    double price

    ){

}
