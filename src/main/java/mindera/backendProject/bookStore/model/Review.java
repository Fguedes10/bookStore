package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static mindera.backendProject.bookStore.util.Messages.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = REVIEW_ID, example = "1")
    private Long id;

    @Schema(description = REVIEW_COMMENT, example = REVIEW_COMMENT_EXAMPLE)
    private String comment;

    @Schema(description = REVIEW_DATE, example = DATE_EXAMPLE)
    private LocalDate commentDate;

    @Schema(description = REVIEW_RATING, example = BOOK_RATING_EXAMPLE)
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