package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;

import mindera.backendProject.bookStore.exception.book.GenreAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.GenreNotFoundException;
import mindera.backendProject.bookStore.model.Genre;
import mindera.backendProject.bookStore.service.bookService.GenreServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/genres")
public class GenreController {

    private final GenreServiceImpl genreService;


    public GenreController(GenreServiceImpl genreService) {
        this.genreService = genreService;
    }


    @Operation(
            summary = "Get all existing genres",
            description = "Get all existing genres"
    )
    @GetMapping("/")
    public ResponseEntity<List<GenreCreateDto>> getGenres() {
        return ResponseEntity.ok(genreService.getAll());
    }


    @Operation(
            summary = "Get genre by id",
            description = "Get genre by id"
    )
    @GetMapping("/id/{genreId}")
    public ResponseEntity<GenreCreateDto> getGenre(@PathVariable("genreId")@Parameter(name = "Genre Id", description = "Genre id", example = "1") Long genreId) throws GenreNotFoundException {
        return new ResponseEntity<>(genreService.getGenre(genreId), HttpStatus.OK);
    }


    @Operation(
            summary = "Get genre by name",
            description = "Get genre by name"
    )
    @GetMapping("/name/{genreName}")
    public ResponseEntity<GenreCreateDto> getGenreByName(@PathVariable("genreName")@Parameter(name = "Genre Name",
            description = "Genre name", example = "Drama") String genreName) throws GenreNotFoundException {
        return new ResponseEntity<>(genreService.getGenreByName(genreName), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new genre",
            description = "Add new genre"
    )
    @PostMapping("/")
    public ResponseEntity<GenreCreateDto> add(@Valid @RequestBody GenreCreateDto genre) throws GenreAlreadyExistsException {
        return new ResponseEntity<>(genreService.add(genre), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete genre by id",
            description = "Delete genre by id"
    )
    @DeleteMapping("/id/{genreId}")
    public ResponseEntity<Genre> delete(@PathVariable("genreId") Long genreId) throws GenreNotFoundException {
        genreService.delete(genreId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
