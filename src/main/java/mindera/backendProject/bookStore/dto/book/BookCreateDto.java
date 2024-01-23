package mindera.backendProject.bookStore.dto.book;

import jakarta.persistence.*;
import mindera.backendProject.bookStore.model.*;

import java.time.LocalDate;
import java.util.List;

public record BookCreateDto(

    String title,
    String isbn,
    Long author,
    String publisher,
    Long genre,
    Long language,
    Long translation,
    Long review,
    int edition,
    LocalDate releaseDate,
    double price

    ){

}
