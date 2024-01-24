package mindera.backendProject.bookStore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String name;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "author", cascade = CascadeType.ALL)
    List<Book> books;




}