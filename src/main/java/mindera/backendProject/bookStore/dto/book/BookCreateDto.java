package mindera.backendProject.bookStore.dto.book;

import jakarta.persistence.*;
import mindera.backendProject.bookStore.model.*;

import java.time.LocalDate;
import java.util.List;

public record BookCreateDto(

    String title,
    String isbn,
    Long authorId,
    String publisher,
    Long genreId,
    Long languageId,
    Long translationId,
    Long reviewId,
    int edition,
    LocalDate releaseDate,
    double price

    ){

}
