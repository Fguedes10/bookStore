package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.PublisherCreateDto;
import mindera.backendProject.bookStore.exception.book.PublisherAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.PublisherNotFoundException;
import mindera.backendProject.bookStore.model.Publisher;
import mindera.backendProject.bookStore.service.bookService.PublisherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/publishers")
public class PublisherController {

    @Autowired
    private PublisherServiceImpl publisherService;

    @Operation(
            summary = "Get all existing publishers",
            description = "Get all existing publishers"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publishers found")})
    @GetMapping("/")
    public ResponseEntity<List<PublisherCreateDto>> getPublishers(){
        return ResponseEntity.ok(publisherService.getAll());
    }


    @Operation(
            summary = "Get publisher by id",
            description = "Get publisher by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publisher found"),
            @ApiResponse(responseCode = "404", description = "Publisher not found")})
    @GetMapping("/id/{publisherId}")
    public ResponseEntity<PublisherCreateDto> getPublisher(@PathVariable("publisherId")@Parameter(name = "Publisher Id", description = "Publisher id", example = "1") Long publisherId) throws PublisherNotFoundException {
        return new ResponseEntity<>(publisherService.getPublisher(publisherId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get publisher by name",
            description = "Get publisher by name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publisher found"),
            @ApiResponse(responseCode = "404", description = "Publisher not found")})
    @GetMapping("/name/{publisherName}")
    public ResponseEntity<PublisherCreateDto> getPublisherByName(@PathVariable("publisherName")@Parameter(name = "Publisher Name", description = "Publisher name", example = "John") String publisherName) throws PublisherNotFoundException{
        return new ResponseEntity<>(publisherService.getPublisherByName(publisherName), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new publisher",
            description = "Add new publisher"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Publisher created"),
            @ApiResponse(responseCode = "409", description = "Publisher already exists")})
    @PostMapping("/")
    public ResponseEntity<PublisherCreateDto> add(@Valid @RequestBody PublisherCreateDto publisher) throws PublisherAlreadyExistsException {
        return new ResponseEntity<>(publisherService.add(publisher), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Add multiple publishers",
            description = "Add multiple publishers"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Publishers created"),
            @ApiResponse(responseCode = "409", description = "Publisher already exists")})
    @PostMapping("/addMultiplePublishers")
    public ResponseEntity<List<PublisherCreateDto>> addMultiplePublishers(@Valid @RequestBody List<PublisherCreateDto> publishers) throws PublisherAlreadyExistsException {
        return new ResponseEntity<>(publisherService.addMultiplePublishers(publishers), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Delete publisher by id",
            description = "Delete publisher by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publisher deleted"),
            @ApiResponse(responseCode = "404", description = "Publisher not found")})
    @DeleteMapping("/id/{publisherId}")
    public ResponseEntity<Publisher> delete(@PathVariable ("publisherId")@Parameter(name = "Publisher Id", description = "Publisher id", example = "1") Long publisherId) throws PublisherNotFoundException {
        publisherService.delete(publisherId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
