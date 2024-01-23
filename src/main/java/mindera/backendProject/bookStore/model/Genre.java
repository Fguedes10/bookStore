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
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String name;

    @ManyToMany(mappedBy = "bookGenres")
    List<Book> genres;



}
