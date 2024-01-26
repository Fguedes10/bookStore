package mindera.backendProject.bookStore.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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


    @ManyToOne(fetch = FetchType.EAGER )
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER )
    private Publisher publisher;

    @ManyToMany(mappedBy = "favoriteBooks", fetch = FetchType.EAGER )
    List<Customer> customersWhoFavorited;

    @ManyToMany(mappedBy = "purchasedBooks", fetch = FetchType.EAGER)
    private List<Customer> customersWhoPurchased;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Genre> genre;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Review> review = new ArrayList<>();

    private Integer edition;

    private int yearRelease;

    private double price;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Translation> translation;


    public void addReview(Review review){
        this.review.add(review);
    }
}
