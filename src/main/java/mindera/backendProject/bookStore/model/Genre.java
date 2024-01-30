package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Schema(description = "Genre id", example = "1")
    private Long id;

    @Column(unique=true)
    @Schema(description = "Genre name", example = "Drama")
    private String name;

    @ManyToMany(mappedBy = "genre", fetch = FetchType.EAGER)
    @Schema(description = "Genre's books", example = "[1, 2, 3]")
    private List<Book> books;

    @ManyToMany(mappedBy = "favoriteGenres", fetch = FetchType.EAGER)
    @Schema(description = "Customer favorite genres", example = "[1, 2, 3]")
    private List<Customer> customerList;




}
