package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "OrderModel id", example = "1")
    private Long id;

    @OneToMany(mappedBy = "orderModel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Schema(description = "Order items associated with the order", example = "[1, 2, 3]")
    private List<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.EAGER)
    @Schema(description = "Customer order", example = "[1, 2, 3]")
    private Customer customer;

    @OneToMany(mappedBy = "orderModel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Schema(description = "Orders downloaded", example = "[1, 2, 3]")
    private List<Download> downloads;

    @OneToOne
    @Schema(description = "Order Invoice", example = "101")
    private Invoice invoice;

    @ManyToMany
    @JoinTable(
            name = "orderModel_book",
            joinColumns = @JoinColumn(name = "orderModel_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Schema(description = "books ordered", example = "[1, 2, 3]")
    private List<Book> books;

    @Schema(description = "date of purchase", example = "2023-01-30")
    private LocalDate purchaseDate;



}
