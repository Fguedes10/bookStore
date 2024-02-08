package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = ORDERITEM_ID, example = ID_EXAMPLE)
    private Long id;

    @OneToOne
    @Schema(description = "Customer", example = ID_EXAMPLE)
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "orderItem_book",
            joinColumns = @JoinColumn(name = "orderItem_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Schema(description = "Set of books to purchase", example = LIST_EXAMPLE)
    private List<Book> booksToPurchase;

    @Schema(description = TOTAL_AMOUNT, example = BOOK_PRICE_EXAMPLE)
    private double amountToPay;

    @Schema(description = TAX_VALUE, example = TAX_VALUE_EXAMPLE)
    private double taxRate = 0.06;

    @Schema(description = "Indicates whether the eBooks were purchased", example = "true")
    private boolean isOrdered;


    public void calculateAmountToPay() {
        List<Book> booksToPurchase = new ArrayList<>(getBooksToPurchase());

        double sumOfBookPrices = booksToPurchase.stream()
                .mapToDouble(Book::getPrice)
                .sum();

        amountToPay = sumOfBookPrices * (1 + taxRate);
    }
}
