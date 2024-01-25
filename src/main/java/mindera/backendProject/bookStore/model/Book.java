package mindera.backendProject.bookStore.model;
import jakarta.persistence.*;
import lombok.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;
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

    @ManyToOne(fetch = FetchType.EAGER)
    private Author author;

    private String publisher;


    @ManyToMany(mappedBy = "favoriteBooks", fetch = FetchType.EAGER)
    Set<Customer> customersWhoFavorited;

    @ManyToMany(mappedBy = "purchasedBooks", fetch = FetchType.EAGER)
    private Set<Customer> customersWhoPurchased;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Genre> genre;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    Set<Review> review;

    private Integer edition;

    private LocalDate releaseDate;

    private double price;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Translation> translation;


}
