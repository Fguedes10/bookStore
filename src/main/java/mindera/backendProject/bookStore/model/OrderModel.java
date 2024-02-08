package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class OrderModel {

    String invoicePath;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = ORDER_ID, example = ID_EXAMPLE)
    private Long id;

    @OneToOne
    @Schema(description = ORDERITEM_ASSOCIATED_WITH_THIS_ORDER, example = LIST_EXAMPLE)
    private OrderItem orderItems;


    @ManyToOne(fetch = FetchType.EAGER)
    @Schema(description = CUSTOMER_ORDER, example = LIST_EXAMPLE)
    private Customer customer;

    @OneToMany(mappedBy = "orderModel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Schema(description = ORDER_DOWNLOADS, example = LIST_EXAMPLE)
    private List<Download> downloads;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "orderModel_book",
            joinColumns = @JoinColumn(name = "orderModel_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )

    @Schema(description = BOOK_ORDERS, example = LIST_EXAMPLE)
    private List<Book> books;

    @Schema(description = PURCHASE_DATE, example = DATE_EXAMPLE)
    private LocalDate purchaseDate;

    @Schema(description = INVOICE_PATH, example = INVOICE_PATH_EXAMPLE)
    private String invoiceFilePath;


    public OrderModel(OrderItem orderItem, Customer customer, List<Book> booksToPurchase, LocalDate purchaseDate) {
        this.orderItems = orderItem;
        this.customer = customer;
        this.books = booksToPurchase;
        this.purchaseDate = purchaseDate;
    }
}
