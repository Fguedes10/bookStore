package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Book id", example = "1")
    private Long id;

    @Column(unique = true)
    @Schema(description = "Book title", example = "The Lord of the Rings")
    private String title;


    @Column(unique = true)
    @Schema(description = "Book ISBN", example = "1234567890")
    private Long isbn;

    @ManyToOne(fetch = FetchType.EAGER)
    @Schema(description = "Book author", example = "[1]")
    private Author author;


    @ManyToOne(fetch = FetchType.EAGER)
    @Schema(description = "Book publisher", example = "[1]")
    private Publisher publisher;

    @ManyToMany(mappedBy = "favoriteBooks", fetch = FetchType.EAGER)
    @Schema(description = "Customer favorite books", example = "[1, 2, 3]")
    List<Customer> customersWhoFavorited;

    @ManyToMany(mappedBy = "purchasedBooks", fetch = FetchType.EAGER)
    @Schema(description = "Customer purchased books", example = "[1, 2, 3]")
    private List<Customer> customersWhoPurchased;

    @ManyToMany(fetch = FetchType.EAGER)
    @Schema(description = "Book genre", example = "[1, 2, 3]")
    List<Genre> genre;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    List<Review> review = new ArrayList<>();

    @Schema(description = "Book edition", example = "1")
    private Integer edition;

    @Schema(description = "Book year release", example = "2000")
    private int yearRelease;

    @Schema(description = "Book price", example = "19.99")
    private double price;

    @ManyToMany(fetch = FetchType.EAGER)
    @Schema(description = "Book translation", example = "[1, 2, 3]")
    List<Translation> translation;

    @ManyToMany(fetch = FetchType.EAGER)
    @Schema(description = "Book's orders", example = "[1, 2, 3]")
    private List<OrderModel> orderModels;

    @ManyToMany(fetch = FetchType.EAGER)
    @Schema(description = "Book's to purchase", example = "[1, 2, 3]")
    private List<OrderItem> orderItems;

    private Double rating;

    private Integer pageCount;

    public Book(String title, Long isbn, Author author, Publisher publisher, List<Genre> genre, Integer edition, int yearRelease, double price, List<Translation> translation) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.edition = edition;
        this.yearRelease = yearRelease;
        this.price = price;
        this.translation = translation;
        this.review.add(new Review("No reviews yet"));
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
