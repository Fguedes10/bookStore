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
public class Download {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = DOWNLOAD_ID, example = ID_EXAMPLE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Schema(description = ORDER_DOWNLOAD, example = ID_EXAMPLE)
    private OrderModel orderModel;

    @Schema(description = DOWNLOAD_DATE, example = DATE_EXAMPLE)
    private LocalDate downloadDate;

    @Column(unique = true)
    @Schema(description = DOWNLOAD_LINK, example = DOWNLOAD_LINK_EXAMPLE)
    private String downloadLink;

}
