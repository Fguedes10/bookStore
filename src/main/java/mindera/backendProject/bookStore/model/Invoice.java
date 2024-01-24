package mindera.backendProject.bookStore.model;

import jakarta.persistence.*;
import lombok.*;
import mindera.backendProject.bookStore.model.Customer;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bookorder_id", nullable = false)
    private BookOrder bookOrder;

    @Column(nullable = false)
    private LocalDateTime issueDate;

    @Column(nullable = false)
    private Double totalAmount;

    //Missing VAT identification number
}
