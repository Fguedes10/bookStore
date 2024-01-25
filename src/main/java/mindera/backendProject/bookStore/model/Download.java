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
public class Download {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bookOrder_id", nullable = false)
    private BookOrder bookOrder;

    @Column(nullable = false)
    private LocalDateTime downloadDate;

    //Missing Download Link???

}
