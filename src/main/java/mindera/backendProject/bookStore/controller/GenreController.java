package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Parameter;

import mindera.backendProject.bookStore.dto.book.GenreCreateDto;

import mindera.backendProject.bookStore.exception.GenreAlreadyExistsException;
import mindera.backendProject.bookStore.exception.GenreNotFoundException;
import mindera.backendProject.bookStore.service.bookService.GenreService;
import mindera.backendProject.bookStore.service.bookService.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/genres")
public class GenreController {

    private final GenreServiceImpl genreService;


    public GenreController(GenreServiceImpl genreService){
        this.genreService = genreService;
    }


    @GetMapping("/")
    public ResponseEntity<List<GenreCreateDto>> getGenres(){
        return ResponseEntity.ok(genreService.getAll());
    }


    @GetMapping("/{genreId}")
    public ResponseEntity<GenreCreateDto> getGenre(@PathVariable("genreId") Long genreId) throws GenreNotFoundException{
        return new ResponseEntity<>(genreService.getGenre(genreId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<GenreCreateDto> getGenreByName(@PathVariable("genreName") String genreName) throws GenreNotFoundException {
        return new ResponseEntity<>(genreService.getGenreByName(genreName), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<GenreCreateDto> add(@RequestBody GenreCreateDto genre) throws GenreAlreadyExistsException {
        GenreCreateDto genreDto = genreService.add(genre);
        return new ResponseEntity<>(genreDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name= "id", description = "Genre id", example = "1") Long id) throws GenreNotFoundException {
        genreService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
