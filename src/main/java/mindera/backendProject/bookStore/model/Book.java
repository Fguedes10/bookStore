package mindera.backendProject.bookStore.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
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

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    private String publisher;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book", cascade = CascadeType.ALL)
    Set<Genre> genres;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    Set<Review> reviews;

    private int edition;

    private LocalDate releaseDate;

    private double price;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "book", cascade = CascadeType.ALL)
    Set<Translation> translations;

}
