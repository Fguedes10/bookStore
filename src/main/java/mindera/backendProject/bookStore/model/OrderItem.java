package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "OrderItem id", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    @Schema(description = "Customer items to purchase", example = "[1, 2, 3] ")
    private Customer customer;

    @OneToOne
    @Schema(description = "Payment for the order item")
    private Payment payment;

    @ManyToMany
    @JoinTable(
            name = "orderItem_book",
            joinColumns = @JoinColumn(name = "orderItem_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Schema(description = "Set of books to purchase", example= "[1, 2, 3]")
    private Set<Book> booksToPurchase;

    @Schema( description = "Total amount to pay", example= "22.70")
    private double amountToPay;

    @Schema(description = "Value Added Tax (VAT)", example = "0.06")
    private static final double TAX_RATE = 0.06;

    @Schema(description = "Ordered or not")
    private boolean ordered;



    public void calculateAmountToPay() {
        List<Book> booksToPurchase = new ArrayList<>(getBooksToPurchase());

        double sumOfBookPrices = booksToPurchase.stream()
                .mapToDouble(Book::getPrice)
                .sum();

        amountToPay = sumOfBookPrices * (1 + TAX_RATE);
    }
}
