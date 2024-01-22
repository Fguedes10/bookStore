package mindera.backendProject.bookStore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    private String comment;

    private LocalDate commentDate;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    public Review() {
    }


}