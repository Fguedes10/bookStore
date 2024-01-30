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
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Author id", example = "1")
    private Long id;

    @Column(unique=true)
    @Schema(description = "Author name", example = "J.R.R. Tolkien")
    private String name;


    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    @Schema(description = "Author's books", example = "[1, 2, 3]")
    List<Book> books;

}