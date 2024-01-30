package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;
import mindera.backendProject.bookStore.exception.book.AuthorAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.AuthorNotFoundException;
import mindera.backendProject.bookStore.exception.book.CannotDeleteException;
import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.service.bookService.AuthorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {



    private final AuthorServiceImpl authorService;


    public AuthorController(AuthorServiceImpl authorService){
        this.authorService = authorService;
    }


    @Operation(
            summary = "Get all existing authors",
            description = "Get all existing authors"
    )
    @GetMapping("/")
    public ResponseEntity<List<AuthorCreateDto>> getAuthors(){
        return ResponseEntity.ok(authorService.getAll());
    }


    @Operation(
            summary = "Get author by id",
            description = "Get author by id"
    )
    @GetMapping("/id/{authorId}")
    public ResponseEntity<AuthorCreateDto> getAuthor(@PathVariable("authorId")@Parameter(name = "Author Id", description = "Author id", example = "1" ) Long authorId) throws AuthorNotFoundException{
        return new ResponseEntity<>(authorService.getAuthor(authorId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get author by name",
            description = "Get author by name"
    )
    @GetMapping("/name/{authorName}")
    public ResponseEntity<AuthorCreateDto> getAuthorByName(@PathVariable("authorName")@Parameter(name = "Author Name", description = "Author name", example = "John") String authorName) throws AuthorNotFoundException{
        return new ResponseEntity<>(authorService.getAuthorByName(authorName), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new author",
            description = "Add new author"
    )
    @PostMapping("/")
    public ResponseEntity<AuthorCreateDto> add(@Valid @RequestBody AuthorCreateDto author) throws AuthorAlreadyExistsException {
        return new ResponseEntity<>(authorService.add(author), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Add multiple authors",
            description = "Add multiple authors"
    )
    @PostMapping("/addMultipleAuthors")
    public ResponseEntity<List<AuthorCreateDto>> addMultipleAuthors(@Valid @RequestBody List<AuthorCreateDto> authors) throws AuthorAlreadyExistsException {
        return new ResponseEntity<>(authorService.addMultipleAuthors(authors), HttpStatus.CREATED);
    }



    @Operation(
            summary = "Delete author by id",
            description = "Delete author by id"
    )
    @DeleteMapping("/id/{authorId}")
    public ResponseEntity<Author> delete(@PathVariable ("authorId")@Parameter(name = "Author Id", description = "Author id", example = "1") Long authorId) throws AuthorNotFoundException,
            CannotDeleteException {
        authorService.delete(authorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}