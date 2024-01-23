package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Parameter;

import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.book.BookUpdateEditionDto;
import mindera.backendProject.bookStore.dto.book.BookUpdatePriceDto;

import mindera.backendProject.bookStore.exception.BookAlreadyExistsException;
import mindera.backendProject.bookStore.exception.BookNotFoundException;
import mindera.backendProject.bookStore.service.bookService.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }


    @GetMapping("/")
    public ResponseEntity<List<BookCreateDto>> getBooks(){
        return ResponseEntity.ok(bookService.getAll());
    }


    @GetMapping("/{bookId")
    public ResponseEntity<BookCreateDto> getBook(@PathVariable("bookId") Long bookId){
        return new ResponseEntity<>(bookService.getBook(bookId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<BookCreateDto> getBookByTitle(@PathVariable("bookTittle") String bookTittle) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.getBookByTittle(bookTittle), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<BookCreateDto> add(@RequestBody BookCreateDto book) throws BookAlreadyExistsException {
        BookCreateDto bookDto = bookService.add(book);
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }

    @PatchMapping("/edition/{id}")
    public ResponseEntity<String> updateEdition(@RequestBody BookUpdateEditionDto book, @PathVariable @Parameter(name = "id", description = "Book id", example = "1") Long id) throws BookNotFoundException{
        bookService.updateEdition(id, book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/price/{id}")
    public ResponseEntity<String> updatePrice(@RequestBody BookUpdatePriceDto book, @PathVariable @Parameter(name = "id", description = "Book id", example = "1") Long id) throws BookNotFoundException{
        bookService.updatePrice(id, book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name= "id", description = "Book id", example = "1") Long id) throws BookNotFoundException {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
