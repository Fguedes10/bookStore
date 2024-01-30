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
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Translation id", example = "1")
    private Long id;

    @Column(unique=true)
    @Schema(description = "Translation name", example = "Portuguese")
    private String name;

    @ManyToMany(mappedBy = "translation", fetch = FetchType.EAGER)
    @Schema(description = "Translation's books", example = "[1, 2, 3]")
    private List<Book> books;

}
