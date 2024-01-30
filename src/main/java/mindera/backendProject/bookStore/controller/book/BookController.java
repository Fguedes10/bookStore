package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.dto.book.BookUpdateEditionDto;
import mindera.backendProject.bookStore.dto.book.BookUpdatePriceDto;

import mindera.backendProject.bookStore.exception.book.*;
import mindera.backendProject.bookStore.service.bookService.BookServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final BookServiceImpl bookService;


    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }


    @Operation(
            summary = "Get all existing books",
            description = "Get all existing books"
    )
    @GetMapping("/")
    public ResponseEntity<List<BookGetDto>> getBooks() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @Operation(
            summary = "Get book by id",
            description = "Get book by id"
    )
    @GetMapping("/search/{bookId}")
    public ResponseEntity<BookGetDto> getBook(@PathVariable("bookId") @Parameter(name = "Book Id", description = "Book id", example = "1") Long bookId) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.getBook(bookId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get book by title",
            description = "Get book by title"
    )
    @GetMapping("/search/{title}")
    public ResponseEntity<BookGetDto> getBookByTitle(@PathVariable("title") @Parameter(name = "Book Title",
            description = "Book title", example = "The Lord of the Rings") String bookTittle) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.getBookByTitle(bookTittle), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new book",
            description = "Add new book"
    )
    @PostMapping("/")
    public ResponseEntity<BookGetDto> add(@RequestBody BookCreateDto book) throws BookAlreadyExistsException, AuthorNotFoundException, PublisherNotFoundException, GenreNotFoundException, TranslationNotFoundException {
        return new ResponseEntity<>(bookService.add(book), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update book",
            description = "Update book"
    )
    @PatchMapping("/edition/{id}")
    public ResponseEntity<BookUpdateEditionDto> updateEdition(@Valid @RequestBody BookUpdateEditionDto book, @PathVariable @Parameter(name = "id", description = "Book id", example = "1") Long id) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.updateEdition(id, book), HttpStatus.OK);
    }

    @Operation(
            summary = "Update book price",
            description = "Update book price"
    )
    @PatchMapping("/price/{id}")
    public ResponseEntity<BookUpdatePriceDto> updatePrice(@Valid @RequestBody BookUpdatePriceDto book, @PathVariable @Parameter(name = "id", description = "Book id", example = "1") Long id) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.updatePrice(id, book), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete book by id",
            description = "Delete book by id"
    )
    @DeleteMapping("/id/{bookId}")
    public ResponseEntity<String> delete(@PathVariable("bookId")@Parameter(name = "Book Id", description = "Book id", example = "1") Long bookId) throws BookNotFoundException {
        bookService.delete(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
