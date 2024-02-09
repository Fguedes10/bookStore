package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static mindera.backendProject.bookStore.util.Messages.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = INVOICE_ID, example = ID_EXAMPLE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @Schema(description = CUSTOMER_INVOICE, example = ID_EXAMPLE)
    private Customer customer;

    @OneToOne
    @Schema(description = ORDER_ASSOCIATED_WITH_THIS_INVOICE, example = ID_EXAMPLE)
    private OrderModel orderModel;

    @Schema(description = INVOICE_DATE, example = DATE_EXAMPLE)
    private LocalDate issueDate;

    @Schema(description = TOTAL_AMOUNT, example = BOOK_PRICE_EXAMPLE)
    private double totalAmount;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = INVOICE_NUMBER, example = ID_EXAMPLE)
    @Column(unique = true)
    private int invoiceNumber = 1000;

    @Schema(description = TAX_VALUE, example = TAX_VALUE_EXAMPLE)
    private double VAT = 0.06;


    public Invoice(Customer customer, OrderModel orderModel, LocalDate issueDate, double totalAmount) {
        this.customer = customer;
        this.orderModel = orderModel;
        this.issueDate = issueDate;
        this.totalAmount = totalAmount;
    }
}

