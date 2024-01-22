package mindera.backendProject.bookStore.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String title;

    @Column(unique=true)
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    private String publisher;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews;

    private int edition;

    private LocalDate releaseDate;

    private double price;




    public Book(){

    }


}
