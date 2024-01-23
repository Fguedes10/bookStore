package mindera.backendProject.bookStore.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String title;

    @Column(unique=true)
    private String isbn;

    private Author author;

    private String publisher;

    private Genre genre;

    private Language language;

    private Review review;

    private int edition;

    private LocalDate releaseDate;

    private double price;

    private Translation translation;




}
