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

    @ManyToOne(fetch = FetchType.EAGER)
    private Author author;

    private String publisher;

    @OneToMany (mappedBy = "book")
    Set<Genre> genres;

    @OneToMany (mappedBy = "book", fetch = FetchType.EAGER)
    Set<Review> reviews;

    private int edition;

    private LocalDate releaseDate;

    private double price;

    @OneToMany (mappedBy = "book", fetch = FetchType.EAGER)
    Set<Translation> translations;

}
