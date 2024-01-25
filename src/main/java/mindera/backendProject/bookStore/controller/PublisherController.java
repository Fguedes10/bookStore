package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Parameter;
import mindera.backendProject.bookStore.dto.book.PublisherCreateDto;
import mindera.backendProject.bookStore.exception.PublisherAlreadyExistsException;
import mindera.backendProject.bookStore.exception.PublisherNotFoundException;
import mindera.backendProject.bookStore.service.bookService.PublisherServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/publishers")
public class PublisherController {

    private final PublisherServiceImpl publisherService;


    public PublisherController(PublisherServiceImpl publisherService){
        this.publisherService = publisherService;
    }


    @GetMapping("/")
    public ResponseEntity<List<PublisherCreateDto>> getPublishers(){
        return ResponseEntity.ok(publisherService.getAll());
    }


    @GetMapping("/{publisherId}")
    public ResponseEntity<PublisherCreateDto> getPublisher(@PathVariable("publisherId") Long publisherId) throws PublisherNotFoundException {
        return new ResponseEntity<>(publisherService.getPublisher(publisherId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PublisherCreateDto> getPublisherByName(@PathVariable("publisherName") String publisherName) throws PublisherNotFoundException{
        return new ResponseEntity<>(publisherService.getPublisherByName(publisherName), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PublisherCreateDto> add(@RequestBody PublisherCreateDto publisher) throws PublisherAlreadyExistsException {
        PublisherCreateDto publisherDto = publisherService.add(publisher);
        return new ResponseEntity<>(publisherDto, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name= "id", description = "Publisher id", example = "1") Long id) throws PublisherNotFoundException {
        publisherService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
