package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Invoice id", example = "1")
    private Long id;

    @ManyToOne ( fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @Schema(description = "Customer invoice", example = "1")
    private Customer customer;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    @Schema(description = "Order items associated with this invoice", example = "[1}")
    private OrderModel orderModel;

    @Schema(description = "invoice release date", example = "2023-01-30")
    private LocalDate issueDate;

    @Schema( description = "Total amount payed", example= "22.70")
    private double totalAmount;

    @Schema(description = "Value Added Tax (VAT)", example = "0.06")
    private double VAT = 0.06;
    //Missing VAT identification number
}
