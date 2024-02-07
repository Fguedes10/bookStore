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
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = GENRE_ID, example = ID_EXAMPLE)
    private Long id;

    @Column(unique=true)
    @Schema(description = GENRE_NAME, example = GENRE_NAME_EXAMPLE)
    private String name;

    @ManyToMany(mappedBy = "genre", fetch = FetchType.EAGER)
    @Schema(description = GENRE_BOOKS, example = LIST_EXAMPLE)
    private List<Book> books;

    @ManyToMany(mappedBy = "favoriteGenres", fetch = FetchType.EAGER)
    @Schema(description = CUSTOMER_FAVORITE_GENRES, example = LIST_EXAMPLE)
    private List<Customer> customerList;




}
