package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.*;

import mindera.backendProject.bookStore.dto.customer.CustomerWhoFavoritedDto;
import mindera.backendProject.bookStore.exception.book.*;
import mindera.backendProject.bookStore.googleBooksApi.GoogleBookInfoDto;
import mindera.backendProject.bookStore.googleBooksApi.GoogleBooksService;
import mindera.backendProject.bookStore.service.bookService.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;


@Tag(name = BOOK_TAG_NAME, description = BOOK_TAG_DESCRIPTION)
@RestController
@RequestMapping("api/v1/books")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private GoogleBooksService googleBooksService;


    @Operation(
            summary = GET_GOOGLE_BOOK_BY_TITLE,
            description = GET_GOOGLE_BOOK_FROM_API
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = BOOK_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = BOOK_NOT_FOUND)})
    @GetMapping("/google/{title}")
    public GoogleBookInfoDto getBooksInfo(@PathVariable("title") @Parameter(name = "Book Title",
            description = "Book title", example = "The Lord of the Rings") String bookTittle) throws BookNotFoundException {
        return googleBooksService.getBookInfo(bookTittle);
    }

    @Operation(
            summary = GET_ALL_EXIST_BOOKS,
            description = GET_ALL_EXIST_BOOKS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = BOOK_FOUND)})
    @GetMapping("/search")
    public ResponseEntity<List<BookGetDto>> getBooks(@RequestParam (defaultValue = "0", required = false) int page,
                                                     @RequestParam (defaultValue = "10", required = false) int size,
                                                     @RequestParam(defaultValue = "title") String searchTerm) {
        return ResponseEntity.ok(bookService.getAll(page, size, searchTerm));
    }

    @Operation(
            summary = GET_BOOK_BY_ID,
            description = GET_BOOK_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = BOOK_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = BOOK_NOT_FOUND)})
    @GetMapping("/search/id/{bookId}")
    public ResponseEntity<BookGetDto> getBook(@PathVariable("bookId") @Parameter(name = "Book Id", description = "Book id", example = "1") Long bookId) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.getBook(bookId), HttpStatus.OK);
    }

    @Operation(
            summary = GET_BOOK_BY_TITLE,
            description = GET_BOOK_BY_TITLE
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = BOOK_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = BOOK_NOT_FOUND)})
    @GetMapping("/search/title/{title}")
    public ResponseEntity<BookGetDto> getBookByTitle(@PathVariable("title") @Parameter(name = "Book Title",
            description = "Book title", example = "The Lord of the Rings") String bookTittle) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.getBookByTitle(bookTittle), HttpStatus.OK);
    }


    @Operation(
            summary = GET_CUSTOMERS_WHO_FAVORITE,
            description = GET_CUSTOMERS_WHO_FAVORITE
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = BOOK_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = BOOK_NOT_FOUND)})
    @GetMapping("/search/whoFavorited/{bookId}")
    public ResponseEntity<List<CustomerWhoFavoritedDto>> getCustomersWhoFavorited(@PathVariable("bookId") @Parameter(name =
            "Book Id",
            description = "Book id", example = "1") Long bookId) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.getCustomersWhoFavorited(bookId), HttpStatus.OK);
    }


    @Operation(
            summary = GET_BOOKS_BY_RELEASE_YEAR,
            description = GET_BOOKS_BY_RELEASE_YEAR
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = BOOK_FOUND),
            @ApiResponse(responseCode = CONFLICT, description = BOOK_NOT_FOUND)})
    @GetMapping("/search/yearRelease/{releaseYear}")
    public ResponseEntity<List<BookYearReleaseInfoDto>> getBooksByYearRelease(@PathVariable("releaseYear") int releaseYear) throws IncorrectReleaseYearException {
        return new ResponseEntity<>(bookService.getBooksByYearRelease(releaseYear), HttpStatus.OK);
    }

    @Operation(
            summary = GET_BOOKS_BY_TRANSLATION,
            description = GET_BOOKS_BY_TRANSLATION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = BOOK_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = BOOK_NOT_FOUND)})
    @GetMapping("/search/booksByTranslation/{translationId}")
    public ResponseEntity<List<BookGetByTranslationDto>> getBooksByTranslation(@PathVariable("translationId") Long translationId) throws TranslationNotFoundException {
        return new ResponseEntity<>(bookService.getBooksByTranslation(translationId), HttpStatus.OK);
    }

    @Operation(
            summary = ADD_NEW_BOOK,
            description = ADD_NEW_BOOK
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = BOOK_CREATED),
            @ApiResponse(responseCode = CONFLICT, description = BOOK_ALREADY_EXISTS),
            @ApiResponse(responseCode = NOT_FOUND, description = NOT_FOUND_AUTHOR_PUBLISHER_TRANSLATION)})
    @PostMapping("/")
    public ResponseEntity<BookGetNewBookDto> add(@RequestBody BookCreateDto book) throws BookAlreadyExistsException,
            AuthorNotFoundException, PublisherNotFoundException, GenreNotFoundException, TranslationNotFoundException {
        return new ResponseEntity<>(bookService.add(book), HttpStatus.CREATED);
    }

    @Operation(
            summary = UPDATE_BOOK,
            description = UPDATE_BOOK
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = BOOK_UPDATED),
            @ApiResponse(responseCode = NOT_FOUND, description = BOOK_NOT_FOUND)})
    @PatchMapping("/edition/{id}")
    public ResponseEntity<BookUpdateEditionDto> updateEdition(@Valid @RequestBody BookUpdateEditionDto book, @PathVariable @Parameter(name = "id", description = "Book id", example = "1") Long id) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.updateEdition(id, book), HttpStatus.OK);
    }

    @Operation(
            summary = UPDATE_BOOK_PRICE,
            description = UPDATE_BOOK_PRICE
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = BOOK_UPDATED),
            @ApiResponse(responseCode = NOT_FOUND, description = BOOK_NOT_FOUND)})
    @PatchMapping("/price/{id}")
    public ResponseEntity<BookUpdatePriceDto> updatePrice(@Valid @RequestBody BookUpdatePriceDto book, @PathVariable @Parameter(name = "id", description = "Book id", example = "1") Long id) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.updatePrice(id, book), HttpStatus.OK);
    }

    @Operation(
            summary = DELETE_BOOK_BY_ID,
            description = DELETE_BOOK_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = BOOK_DELETED),
            @ApiResponse(responseCode = NOT_FOUND, description = BOOK_NOT_FOUND)})
    @DeleteMapping("/id/{bookId}")
    public ResponseEntity<String> delete(@PathVariable("bookId") @Parameter(name = "Book Id", description = "Book id", example = "1") Long bookId) throws BookNotFoundException {
        bookService.delete(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
