package mindera.backendProject.bookStore.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    private int edition;

    private double price;




    public Book(){

    }

    public Book(String title, int edition, double price) {
        this.title = title;
        this.edition = edition;
        this.price = price;
    }
}
