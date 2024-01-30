package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Publisher id", example = "1")
    private Long id;

    @Column(unique=true)
    @Schema(description = "Publisher name", example = "Porto Editora")
    private String name;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
    @Schema(description = "Publisher's books", example = "[1, 2, 3]")
    List<Book> books;


}
