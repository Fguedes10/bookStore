package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.aspect.ExceptionsHandler;
import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;
import mindera.backendProject.bookStore.exception.AuthorAlreadyExistsException;
import mindera.backendProject.bookStore.exception.AuthorNotFoundException;
import mindera.backendProject.bookStore.exception.CannotDeleteException;
import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.service.bookService.AuthorServiceImpl;
import mindera.backendProject.bookStore.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {



    private final AuthorServiceImpl authorService;


    public AuthorController(AuthorServiceImpl authorService){
        this.authorService = authorService;
    }


    @GetMapping("/")
    public ResponseEntity<List<AuthorCreateDto>> getAuthors(){
        return ResponseEntity.ok(authorService.getAll());
    }


    @GetMapping("/id/{authorId}")
    public ResponseEntity<AuthorCreateDto> getAuthor(@PathVariable("authorId") Long authorId) throws AuthorNotFoundException{
        return new ResponseEntity<>(authorService.getAuthor(authorId), HttpStatus.OK);
    }

    @GetMapping("/name/{authorName}")
    public ResponseEntity<AuthorCreateDto> getAuthorByName(@PathVariable("authorName") String authorName) throws AuthorNotFoundException{
        return new ResponseEntity<>(authorService.getAuthorByName(authorName), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AuthorCreateDto> add(@Valid @RequestBody AuthorCreateDto author, BindingResult bindingResult) throws AuthorAlreadyExistsException {
       if(bindingResult.hasErrors()){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
        AuthorCreateDto authorDto = authorService.add(author);
        return new ResponseEntity<>(authorDto, HttpStatus.CREATED);
    }


    @DeleteMapping("/id/{authorId}")
    public ResponseEntity<Author> delete(@PathVariable ("authorId") Long authorId) throws AuthorNotFoundException, CannotDeleteException {
        authorService.delete(authorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}