package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;

import mindera.backendProject.bookStore.exception.book.GenreAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.GenreNotFoundException;
import mindera.backendProject.bookStore.model.Genre;
import mindera.backendProject.bookStore.service.bookService.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/genres")
public class GenreController {

    @Autowired
    private GenreServiceImpl genreService;

    @Operation(
            summary = "Get all existing genres",
            description = "Get all existing genres"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genres found")})
    @GetMapping("/")
    public ResponseEntity<List<GenreCreateDto>> getGenres() {
        return ResponseEntity.ok(genreService.getAll());
    }


    @Operation(
            summary = "Get genre by id",
            description = "Get genre by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre found"),
            @ApiResponse(responseCode = "404", description = "Genre not found")})
    @GetMapping("/id/{genreId}")
    public ResponseEntity<GenreCreateDto> getGenre(@PathVariable("genreId")@Parameter(name = "Genre Id", description = "Genre id", example = "1") Long genreId) throws GenreNotFoundException {
        return new ResponseEntity<>(genreService.getGenre(genreId), HttpStatus.OK);
    }


    @Operation(
            summary = "Get genre by name",
            description = "Get genre by name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre found"),
            @ApiResponse(responseCode = "404", description = "Genre not found")})
    @GetMapping("/name/{genreName}")
    public ResponseEntity<GenreCreateDto> getGenreByName(@PathVariable("genreName")@Parameter(name = "Genre Name",
            description = "Genre name", example = "Drama") String genreName) throws GenreNotFoundException {
        return new ResponseEntity<>(genreService.getGenreByName(genreName), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new genre",
            description = "Add new genre"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Genre created"),
            @ApiResponse(responseCode = "409", description = "Genre already exists")})
    @PostMapping("/")
    public ResponseEntity<GenreCreateDto> add(@Valid @RequestBody GenreCreateDto genre) throws GenreAlreadyExistsException {
        return new ResponseEntity<>(genreService.add(genre), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Add multiple genres",
            description = "Add multiple genres"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Genres created"),
            @ApiResponse(responseCode = "409", description = "Genre already exists")})
    @PostMapping("/addMultipleGenres")
    public ResponseEntity<List<GenreCreateDto>> addMultipleGenres(@Valid @RequestBody List<GenreCreateDto> genres) throws GenreAlreadyExistsException {
        return new ResponseEntity<>(genreService.addMultipleGenres(genres), HttpStatus.CREATED);
    }




    @Operation(
            summary = "Delete genre by id",
            description = "Delete genre by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre deleted"),
            @ApiResponse(responseCode = "404", description = "Genre not found")})
    @DeleteMapping("/id/{genreId}")
    public ResponseEntity<Genre> delete(@PathVariable("genreId") Long genreId) throws GenreNotFoundException {
        genreService.delete(genreId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
