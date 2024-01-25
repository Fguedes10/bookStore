package mindera.backendProject.bookStore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private Long nif;


    private String favoriteGenre;

    @ManyToMany
    @JoinTable(
            name = "customer_favorite_books",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )

    private Set<Book> favoriteBooks;

    @ManyToMany
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

