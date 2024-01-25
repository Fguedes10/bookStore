package mindera.backendProject.bookStore.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String title;

    @Column(unique=true)
    private Long isbn;

    @ManyToOne
    private Author author;

    private String publisher;


    @ManyToMany(mappedBy = "favoriteBooks")
    Set<Customer> customersWhoFavorited;

    @ManyToMany(mappedBy = "purchasedBooks")
    private Set<Customer> customersWhoPurchased;

    @ManyToMany
    Set<Genre> genre;

    @OneToMany(mappedBy = "book")
    Set<Review> review;

    private Integer edition;

    private LocalDate releaseDate;

    private double price;

    @ManyToMany
    Set<Translation> translation;


}
