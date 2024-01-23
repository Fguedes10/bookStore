package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Parameter;
import mindera.backendProject.bookStore.dto.book.TranslationCreateDto;
import mindera.backendProject.bookStore.exception.*;
import mindera.backendProject.bookStore.service.bookService.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/translation")
public class TranslationController {


    private final TranslationService translationService;

    @Autowired
    public TranslationController(TranslationService translationService){
        this.translationService = translationService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TranslationCreateDto>> getTranslation(){
        return ResponseEntity.ok(translationService.getAll());
    }


    @GetMapping("/{translationId}")
    public ResponseEntity<TranslationCreateDto> getTranslation(@PathVariable("translationId") Long translationId) throws TranslationNotFoundException {
        return new ResponseEntity<>(translationService.getTranslation(translationId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<TranslationCreateDto> getTranslationByName(@PathVariable("translationName") String translationName) throws TranslationNotFoundException {
        return new ResponseEntity<>(translationService.getTranslationByName(translationName), HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<TranslationCreateDto> add(@RequestBody TranslationCreateDto translation) throws TranslationAlreadyExistsException {
        TranslationCreateDto translationDto = translationService.add(translation);
        return new ResponseEntity<>(translationDto, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name= "id", description = "Translation id", example = "1") Long id) throws TranslationNotFoundException {
        translationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
