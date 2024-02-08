package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;
import mindera.backendProject.bookStore.exception.book.AuthorAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.AuthorNotFoundException;
import mindera.backendProject.bookStore.exception.book.CannotDeleteException;
import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.service.bookService.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;

@Tag(name = AUTHOR_TAG_NAME, description = AUTHOR_TAG_DESCRIPTION)
@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {


    @Autowired
    private AuthorServiceImpl authorService;


    @Operation(
            summary = GET_ALL_EXIST_AUTHORS,
            description = GET_ALL_EXIST_AUTHORS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = AUTHORS_FOUND)})

    @GetMapping("/")
    public ResponseEntity<List<AuthorCreateDto>> getAuthors(@RequestParam (defaultValue = "0", required = false) int page,
                                                            @RequestParam (defaultValue = "10", required = false) int size,
                                                            @RequestParam(defaultValue = "name") String searchTerm){
        return ResponseEntity.ok(authorService.getAll(page, size, searchTerm));
    }


    @Operation(
            summary = GET_AUTHOR_BY_ID,
            description = GET_AUTHOR_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = AUTHOR_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = AUTHOR_NOT_FOUND)})
    @GetMapping("/id/{authorId}")
    public ResponseEntity<AuthorCreateDto> getAuthor(@PathVariable("authorId")@Parameter(name = "Author Id", description = "Author id", example = "1" ) Long authorId) throws AuthorNotFoundException{
        return new ResponseEntity<>(authorService.getAuthor(authorId), HttpStatus.OK);
    }

    @Operation(
            summary = GET_AUTHOR_BY_NAME,
            description = GET_AUTHOR_BY_NAME
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = AUTHOR_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = AUTHOR_NOT_FOUND)})
    @GetMapping("/name/{authorName}")
    public ResponseEntity<AuthorCreateDto> getAuthorByName(@PathVariable("authorName")@Parameter(name = "Author Name", description = "Author name", example = "John") String authorName) throws AuthorNotFoundException{
        return new ResponseEntity<>(authorService.getAuthorByName(authorName), HttpStatus.OK);
    }

    @Operation(
            summary = ADD_NEW_AUTHOR,
            description = ADD_NEW_AUTHOR
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = AUTHOR_CREATED)})
    @PostMapping("/")
    public ResponseEntity<AuthorCreateDto> add(@Valid @RequestBody AuthorCreateDto author) throws AuthorAlreadyExistsException {
        return new ResponseEntity<>(authorService.add(author), HttpStatus.CREATED);
    }


    @Operation(
            summary = ADD_MULTIPLE_AUTHORS,
            description = ADD_MULTIPLE_AUTHORS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = AUTHOR_CREATED),
            @ApiResponse(responseCode = CONFLICT, description = AUTHOR_ALREADY_EXISTS)})
    @PostMapping("/addMultipleAuthors")
    public ResponseEntity<List<AuthorCreateDto>> addMultipleAuthors(@Valid @RequestBody List<AuthorCreateDto> authors) throws AuthorAlreadyExistsException {
        return new ResponseEntity<>(authorService.addMultipleAuthors(authors), HttpStatus.CREATED);
    }



    @Operation(
            summary = DELETE_AUTHOR_BY_ID,
            description = DELETE_AUTHOR_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = AUTHOR_DELETED),
            @ApiResponse(responseCode = NOT_FOUND, description = AUTHOR_NOT_FOUND)})
    @DeleteMapping("/id/{authorId}")
    public ResponseEntity<Author> delete(@PathVariable ("authorId")@Parameter(name = "Author Id", description = "Author id", example = "1") Long authorId) throws AuthorNotFoundException,
            CannotDeleteException {
        authorService.delete(authorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}