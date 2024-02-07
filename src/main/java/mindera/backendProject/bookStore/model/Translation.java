package mindera.backendProject.bookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = TRANSLATION_ID, example = ID_EXAMPLE)
    private Long id;

    @Column(unique=true)
    @Schema(description = TRANSLATION_NAME, example = LANGUAGE_EXAMPLE)
    private String name;

    @ManyToMany(mappedBy = "translation", fetch = FetchType.EAGER)
    @Schema(description = BOOK_WITH_TRANSLATION, example = LIST_EXAMPLE)
    private List<Book> books;

}
