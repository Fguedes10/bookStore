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
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = AUTHOR_ID, example = ID_EXAMPLE)
    private Long id;

    @Column(unique=true)
    @Schema(description = AUTHOR_NAME, example = AUTHOR_NAME_EXAMPLE)
    private String name;


    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    @Schema(description = AUTHOR_BOOKS, example = LIST_EXAMPLE)
    List<Book> books;

}