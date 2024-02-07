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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = PUBLISHER_ID, example = ID_EXAMPLE)
    private Long id;

    @Column(unique=true)
    @Schema(description = PUBLISHER_NAME, example = PUBLISHER_NAME_EXAMPLE)
    private String name;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
    @Schema(description = PUBLISHER_BOOKS, example = LIST_EXAMPLE)
    List<Book> books;


}
