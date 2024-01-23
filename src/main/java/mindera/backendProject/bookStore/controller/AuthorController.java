package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Parameter;
import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;
import mindera.backendProject.bookStore.exception.AuthorAlreadyExistsException;
import mindera.backendProject.bookStore.exception.AuthorNotFoundException;
import mindera.backendProject.bookStore.service.bookService.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }


    @GetMapping("/")
    public ResponseEntity<List<AuthorCreateDto>> getAuthors(){
        return ResponseEntity.ok(authorService.getAll());
    }


    @GetMapping("/{authorId")
    public ResponseEntity<AuthorCreateDto> getAuthor(@PathVariable("authorId") Long authorId) throws AuthorNotFoundException{
        return new ResponseEntity<>(authorService.getAuthor(authorId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<AuthorCreateDto> getAuthorByName(@PathVariable("authorName") String authorName) throws AuthorNotFoundException{
        return new ResponseEntity<>(authorService.getAuthorByName(authorName), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AuthorCreateDto> add(@RequestBody AuthorCreateDto author) throws AuthorAlreadyExistsException {
        AuthorCreateDto authorDto = authorService.add(author);
        return new ResponseEntity<>(authorDto, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name= "id", description = "Author id", example = "1") Long id) throws AuthorNotFoundException {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}