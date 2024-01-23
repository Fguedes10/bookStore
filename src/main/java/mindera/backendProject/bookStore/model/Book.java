package mindera.backendProject.bookStore.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    private String publisher;

    @ManyToMany
    @JoinTable(
            name= "books_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Genre genre;


    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @OneToMany(mappedBy = "book")
    private Review review;

    private int edition;

    private LocalDate releaseDate;

    private double price;
    private Translation translation;




}
