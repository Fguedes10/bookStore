package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.PublisherCreateDto;
import mindera.backendProject.bookStore.exception.PublisherAlreadyExistsException;
import mindera.backendProject.bookStore.exception.PublisherNotFoundException;
import mindera.backendProject.bookStore.model.Publisher;
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


    @GetMapping("/id/{publisherId}")
    public ResponseEntity<PublisherCreateDto> getPublisher(@PathVariable("publisherId") Long publisherId) throws PublisherNotFoundException {
        return new ResponseEntity<>(publisherService.getPublisher(publisherId), HttpStatus.OK);
    }

    @GetMapping("/name/{publisherName}")
    public ResponseEntity<PublisherCreateDto> getPublisherByName(@PathVariable("publisherName") String publisherName) throws PublisherNotFoundException{
        return new ResponseEntity<>(publisherService.getPublisherByName(publisherName), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PublisherCreateDto> add(@Valid @RequestBody PublisherCreateDto publisher) throws PublisherAlreadyExistsException {
        return new ResponseEntity<>(publisherService.add(publisher), HttpStatus.CREATED);
    }


    @DeleteMapping("/id/{publisherId}")
    public ResponseEntity<Publisher> delete(@PathVariable ("publisherId") Long publisherId) throws PublisherNotFoundException {
        publisherService.delete(publisherId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
