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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Payment id", example = "1")
    private Long id;

    @OneToOne
    @JoinColumn(name = "OrderItem_id", nullable = false, unique = true)
    @Schema(description = "Payment for the order Items", example = "1")
    private OrderItem orderItem;


    @Column(nullable = false)
    @Schema(description = "Date of payment", example = "2023-01-30")
    private LocalDate paymentDate;

    @Column(nullable = false)
    @Schema(description = "Price of the purchase", example = "5.99")
    private double amount;

    @Schema(description = "Indicates whether the payment was successful", example = "true")
    private boolean isSuccessful;


}
