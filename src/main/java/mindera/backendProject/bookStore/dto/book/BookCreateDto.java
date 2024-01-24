package mindera.backendProject.bookStore.dto.book;

import jakarta.persistence.*;
import mindera.backendProject.bookStore.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record BookCreateDto(

    String title,
    String isbn,
    Long authorId,
    String publisher,
    Set<Genre> genres,
    Set<Translation> translations,
    Set<Review> reviews,
    int edition,
    LocalDate releaseDate,
    double price

    ){

}
