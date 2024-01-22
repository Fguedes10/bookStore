package mindera.backendProject.bookStore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String isbn;

    private boolean anonymous;

    private LocalDate dateOfComment;

    private String comment;


    public Review() {
    }

    public Review(String isbn, boolean anonymous, LocalDate dateOfComment, String comment) {
        this.isbn = isbn;
        this.anonymous = anonymous;
        this.dateOfComment = dateOfComment;
        this.comment = comment;
    }
}