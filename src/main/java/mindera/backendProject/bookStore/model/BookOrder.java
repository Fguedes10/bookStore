package mindera.backendProject.bookStore.model;

import jakarta.persistence.*;
import lombok.*;
import mindera.backendProject.bookStore.model.Customer;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class BookOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "bookOrder")
    private List<Download> downloads;

    @Column(nullable = false)
    private LocalDateTime requestDate;

    //Missing order status and download link??

}
