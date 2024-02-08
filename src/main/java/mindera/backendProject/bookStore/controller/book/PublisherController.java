package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static mindera.backendProject.bookStore.util.Messages.*;

@Tag(name = PUBLISHER_TAG_NAME, description = PUBLISHER_TAG_DESCRIPTION)
@RestController
@RequestMapping("api/v1/publishers")
public class PublisherController {

    @Autowired
    private PublisherServiceImpl publisherService;

    @Operation(
            summary = GET_ALL_EXIST_PUBLISHERS,
            description = GET_ALL_EXIST_PUBLISHERS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = PUBLISHERS_FOUND)})
    @GetMapping("/search")
    public ResponseEntity<List<PublisherCreateDto>> getPublishers(@RequestParam (defaultValue = "0", required = false) int page,
                                                                  @RequestParam (defaultValue = "10", required = false) int size,
                                                                  @RequestParam(defaultValue = "name") String searchTerm){
        return ResponseEntity.ok(publisherService.getAll(page, size, searchTerm));
    }


    @Operation(
            summary = GET_PUBLISHER_BY_ID,
            description = GET_PUBLISHER_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = PUBLISHER_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = PUBLISHER_FOUND)})
    @GetMapping("/id/{publisherId}")
    public ResponseEntity<PublisherCreateDto> getPublisher(@PathVariable("publisherId")@Parameter(name = "Publisher Id", description = "Publisher id", example = "1") Long publisherId) throws PublisherNotFoundException {
        return new ResponseEntity<>(publisherService.getPublisher(publisherId), HttpStatus.OK);
    }

    @Operation(
            summary = GET_PUBLISHER_BY_NAME,
            description = GET_PUBLISHER_BY_NAME
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = PUBLISHER_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = PUBLISHER_NOT_FOUND)})
    @GetMapping("/name/{publisherName}")
    public ResponseEntity<PublisherCreateDto> getPublisherByName(@PathVariable("publisherName")@Parameter(name = "Publisher Name", description = "Publisher name", example = "John") String publisherName) throws PublisherNotFoundException{
        return new ResponseEntity<>(publisherService.getPublisherByName(publisherName), HttpStatus.OK);
    }

    @Operation(
            summary = ADD_NEW_PUBLISHER,
            description = ADD_NEW_PUBLISHER
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = PUBLISHER_CREATED),
            @ApiResponse(responseCode = CONFLICT, description = PUBLISHER_ALREADY_EXISTS)})
    @PostMapping("/")
    public ResponseEntity<PublisherCreateDto> add(@Valid @RequestBody PublisherCreateDto publisher) throws PublisherAlreadyExistsException {
        return new ResponseEntity<>(publisherService.add(publisher), HttpStatus.CREATED);
    }

    @Operation(
            summary = ADD_MULTIPLE_PUBLISHERS,
            description = ADD_MULTIPLE_PUBLISHERS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = PUBLISHERS_CREATED),
            @ApiResponse(responseCode = CONFLICT, description = PUBLISHER_ALREADY_EXISTS)})
    @PostMapping("/addMultiplePublishers")
    public ResponseEntity<List<PublisherCreateDto>> addMultiplePublishers(@Valid @RequestBody List<PublisherCreateDto> publishers) throws PublisherAlreadyExistsException {
        return new ResponseEntity<>(publisherService.addMultiplePublishers(publishers), HttpStatus.CREATED);
    }


    @Operation(
            summary = DELETE_PUBLISHER_BY_ID,
            description = DELETE_PUBLISHER_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = PUBLISHER_DELETED),
            @ApiResponse(responseCode = NOT_FOUND, description = PUBLISHER_NOT_FOUND)})
    @DeleteMapping("/id/{publisherId}")
    public ResponseEntity<Publisher> delete(@PathVariable ("publisherId")@Parameter(name = "Publisher Id", description = "Publisher id", example = "1") Long publisherId) throws PublisherNotFoundException {
        publisherService.delete(publisherId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
