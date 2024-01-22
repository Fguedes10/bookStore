package mindera.backendProject.bookStore.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "language")
    private List<Book> books;
}
