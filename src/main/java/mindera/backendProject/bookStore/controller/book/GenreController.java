package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static mindera.backendProject.bookStore.util.Messages.*;

@Tag(name = GENRE_TAG_NAME, description = GENRE_TAG_DESCRIPTION)
@RestController
@RequestMapping("api/v1/genres")
public class GenreController {

    @Autowired
    private GenreServiceImpl genreService;

    @Operation(
            summary = GET_ALL_EXIST_GENRES,
            description = GET_ALL_EXIST_GENRES
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = GENRES_FOUND)})
    @GetMapping("/")
    public ResponseEntity<List<GenreCreateDto>> getGenres() {
        return ResponseEntity.ok(genreService.getAll());
    }


    @Operation(
            summary = GET_GENRE_BY_ID,
            description = GET_GENRE_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = GENRE_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = GENRE_NOT_FOUND)})
    @GetMapping("/id/{genreId}")
    public ResponseEntity<GenreCreateDto> getGenre(@PathVariable("genreId")@Parameter(name = "Genre Id", description = "Genre id", example = "1") Long genreId) throws GenreNotFoundException {
        return new ResponseEntity<>(genreService.getGenre(genreId), HttpStatus.OK);
    }


    @Operation(
            summary = GET_GENRE_BY_NAME,
            description = GET_GENRE_BY_NAME
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = GENRE_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = GENRE_NOT_FOUND)})
    @GetMapping("/name/{genreName}")
    public ResponseEntity<GenreCreateDto> getGenreByName(@PathVariable("genreName")@Parameter(name = "Genre Name",
            description = "Genre name", example = "Drama") String genreName) throws GenreNotFoundException {
        return new ResponseEntity<>(genreService.getGenreByName(genreName), HttpStatus.OK);
    }

    @Operation(
            summary = ADD_NEW_GENRE,
            description = ADD_NEW_GENRE
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = GENRE_CREATED),
            @ApiResponse(responseCode = CONFLICT, description = GENRE_ALREADY_EXISTS)})
    @PostMapping("/")
    public ResponseEntity<GenreCreateDto> add(@Valid @RequestBody GenreCreateDto genre) throws GenreAlreadyExistsException {
        return new ResponseEntity<>(genreService.add(genre), HttpStatus.CREATED);
    }


    @Operation(
            summary = ADD_MULTIPLE_GENRES,
            description = ADD_MULTIPLE_GENRES
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = GENRES_CREATED),
            @ApiResponse(responseCode = CONFLICT, description = GENRE_ALREADY_EXISTS)})
    @PostMapping("/addMultipleGenres")
    public ResponseEntity<List<GenreCreateDto>> addMultipleGenres(@Valid @RequestBody List<GenreCreateDto> genres) throws GenreAlreadyExistsException {
        return new ResponseEntity<>(genreService.addMultipleGenres(genres), HttpStatus.CREATED);
    }




    @Operation(
            summary = DELETE_GENRE_BY_ID,
            description = DELETE_GENRE_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = GENRE_DELETED),
            @ApiResponse(responseCode = NOT_FOUND, description = GENRE_NOT_FOUND)})
    @DeleteMapping("/id/{genreId}")
    public ResponseEntity<Genre> delete(@PathVariable("genreId") Long genreId) throws GenreNotFoundException {
        genreService.delete(genreId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
