package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mindera.backendProject.bookStore.util.Messages.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table

public class Book {

    @ManyToMany(mappedBy = "favoriteBooks", fetch = FetchType.EAGER)
    @Schema(description = CUSTOMER_FAVORITE_BOOKS, example = LIST_EXAMPLE)
    List<Customer> customersWhoFavorited;

    @ManyToMany(fetch = FetchType.EAGER)
    @Schema(description = BOOK_GENRES, example = LIST_EXAMPLE)
    List<Genre> genre;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    List<Review> review = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Schema(description = BOOK_TRANSLATIONS, example = LIST_EXAMPLE)
    List<Translation> translation;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = BOOK_ID, example = ID_EXAMPLE)
    private Long id;

    @Column(unique = true)
    @Schema(description = BOOK_TITLE, example = BOOK_TITLE_EXAMPLE)
    private String title;

    @Column(unique = true)
    @Schema(description = BOOK_ISBN, example = BOOK_ISBN_EXAMPLE)
    private Long isbn;

    @ManyToOne(fetch = FetchType.EAGER)
    @Schema(description = BOOK_AUTHOR, example = ID_EXAMPLE)
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @Schema(description = BOOK_PUBLISHER, example = ID_EXAMPLE)
    private Publisher publisher;

    @ManyToMany(mappedBy = "purchasedBooks", fetch = FetchType.EAGER)
    @Schema(description = CUSTOMERS_WHO_PURCHASED, example = LIST_EXAMPLE)
    private List<Customer> customersWhoPurchased;

    @Schema(description = BOOK_EDITION, example = ID_EXAMPLE)
    private Integer edition;

    @Schema(description = BOOK_RELEASE_YEAR, example = BOOK_RELEASE_YEAR_EXAMPLE)
    private int yearRelease;

    @Schema(description = BOOK_PRICE, example = BOOK_PRICE_EXAMPLE)
    private double price;

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    @Schema(description = BOOK_ORDERS, example = LIST_EXAMPLE)
    private List<OrderModel> orderModels;

    @ManyToMany(fetch = FetchType.EAGER)
    @Schema(description = BOOKS_TO_PURCHASE, example = LIST_EXAMPLE)
    private List<OrderItem> orderItems;

    @Schema(description = BOOK_RATING, example = BOOK_RATING_EXAMPLE)
    private Double rating;
    @Schema(description = BOOK_PAGE_COUNT, example = BOOK_PAGE_COUNT_EXAMPLE)
    private Integer pageCount;


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
