package mindera.backendProject.bookStore.model;

import jakarta.persistence.*;
import lombok.*;

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
    private Long id;

    @Column(unique=true)
    private String isbn;

    private boolean anonymous;

    private String comment;

    private LocalDate commentDate;

    private Book book;




}