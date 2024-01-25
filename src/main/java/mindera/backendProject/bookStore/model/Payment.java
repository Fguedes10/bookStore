package mindera.backendProject.bookStore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
 @Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Payment {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "bookOrder_id", nullable = false, unique = true)
    private BookOrder bookOrder;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private Double amount;
}
