package mindera.backendProject.bookStore.model.transaction;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Download {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bookorder_id", nullable = false)
    private BookOrder bookOrder;

    @Column(nullable = false)
    private LocalDateTime downloadDate;

    //Missing Download Link???

}
