package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Review id", example = "1")
    private Long id;

    @Schema(description = "Review comment", example = "I like this book, because ...")
    private String comment;

    @Schema(description = "Review date", example = "2022-01-01")
    private LocalDate commentDate;

    @Schema(description = "Review rating", example = "5")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Book book;


    public Review(String comment) {
        this.comment = comment;
    }

    public Review(Book book, String comment) {
        this.book = book;
        this.comment = comment;
        this.commentDate = LocalDate.now();
    }


}