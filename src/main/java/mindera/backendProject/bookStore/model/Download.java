package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    @Schema(description = "Download id", example = "1")
    private Long id;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "order_id")
   @Schema(description = "Book's download", example = "1")
   private OrderModel orderModel;

   @Schema(description = "Download date", example = "2023-01-30")
   private LocalDate downloadDate;

    //Missing Download Link???

}
