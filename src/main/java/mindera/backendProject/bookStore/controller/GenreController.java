package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;

import mindera.backendProject.bookStore.exception.GenreAlreadyExistsException;
import mindera.backendProject.bookStore.exception.GenreNotFoundException;
import mindera.backendProject.bookStore.model.Genre;
import mindera.backendProject.bookStore.service.bookService.GenreServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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


    @GetMapping("/id/{genreId}")
    public ResponseEntity<GenreCreateDto> getGenre(@PathVariable("genreId") Long genreId) throws GenreNotFoundException{
        return new ResponseEntity<>(genreService.getGenre(genreId), HttpStatus.OK);
    }

    @GetMapping("/name/{genreName}")
    public ResponseEntity<GenreCreateDto> getGenreByName(@PathVariable("genreName") String genreName) throws GenreNotFoundException {
        return new ResponseEntity<>(genreService.getGenreByName(genreName), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<GenreCreateDto> add(@Valid @RequestBody GenreCreateDto genre, BindingResult bindingResult) throws GenreAlreadyExistsException {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        GenreCreateDto genreDto = genreService.add(genre);
        return new ResponseEntity<>(genreDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/id/{genreId}")
    public ResponseEntity<Genre> delete(@PathVariable ("genreId") Long genreId) throws GenreNotFoundException {
        genreService.delete(genreId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
