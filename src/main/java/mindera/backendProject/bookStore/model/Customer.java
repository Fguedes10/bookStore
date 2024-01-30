package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Customer id", example = "1")
    private Long id;

    @Schema(description = "Customer first name", example = "Joaquim")
    private String firstName;
    @Schema(description = "Customer last name", example = "Verde")
    private String lastName;

    @Schema(description = "Customer username", example = "joaquimverde")
    @Column(unique = true)
    private String username;

    @Schema(description = "Customer password", example = "JVerde123")
    private String password;

    @Schema(description = "Customer email", example = "jverde@me.com")
    @Column(unique = true)
    private String email;

    @Schema(description = "Customer NIF", example = "123456789")
    @Column(unique = true)
    private Long nif;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_favorite_genres",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @Schema(description = "Customer favorite genres", example = "[1, 2, 3]")
    private List<Genre> favoriteGenres;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_favorite_books",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Schema(description = "Customer favorite books", example = "[1, 2, 3]")
    private List<Book> favoriteBooks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_purchased_books",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> purchasedBooks;


    @OneToMany(mappedBy = "customer")
    private Set<Invoice> invoices;


    @OneToMany(mappedBy = "customer")
    private Set<BookOrder> bookOrders;




}

