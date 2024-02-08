package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

import static mindera.backendProject.bookStore.util.Messages.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = CUSTOMER_ID, example = ID_EXAMPLE)
    private Long id;

    @Schema(description = CUSTOMER_FIRSTNAME, example = CUSTOMER_FIRSTNAME_EXAMPLE)
    private String firstName;
    @Schema(description = CUSTOMER_LASTNAME, example = CUSTOMER_LASTNAME_EXAMPLE)
    private String lastName;

    @Schema(description = CUSTOMER_USERNAME, example = CUSTOMER_USERNAME_EXAMPLE)
    @Column(unique = true)
    private String username;

    @Schema(description = CUSTOMER_PASSWORD, example = CUSTOMER_PASSWORD_EXAMPLE)
    private String password;

    @Schema(description = CUSTOMER_EMAIL, example = CUSTOMER_EMAIL_EXAMPLE)
    @Column(unique = true)
    private String email;

    @Schema(description = CUSTOMER_NIF, example = CUSTOMER_NIF_EXAMPLE)
    @Column(unique = true)
    private Long nif;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_favorite_genres",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @Schema(description = CUSTOMER_FAVORITE_GENRES, example = LIST_EXAMPLE)
    private List<Genre> favoriteGenres;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_favorite_books",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Schema(description = CUSTOMER_FAVORITE_BOOKS, example = LIST_EXAMPLE)
    private List<Book> favoriteBooks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_purchased_books",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Schema(description = PURCHASED_BOOKS, example = LIST_EXAMPLE)
    private Set<Book> purchasedBooks;


    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Schema(description = CUSTOMER_INVOICES, example = LIST_EXAMPLE)
    private Set<Invoice> invoices;

    @OneToOne
    @Schema(description = CUSTOMER_ITEMS_TO_PURCHASE, example = LIST_EXAMPLE)
    private OrderItem orderItem;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Schema(description = CUSTOMER_ORDERS, example = LIST_EXAMPLE)
    private Set<OrderModel> orderModels;

}

