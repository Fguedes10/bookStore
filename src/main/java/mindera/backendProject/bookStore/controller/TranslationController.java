package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.TranslationCreateDto;
import mindera.backendProject.bookStore.exception.*;
import mindera.backendProject.bookStore.service.bookService.TranslationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/translations")
public class TranslationController {


    private final TranslationServiceImpl translationService;


    public TranslationController(TranslationServiceImpl translationService){
        this.translationService = translationService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TranslationCreateDto>> getTranslation(){
        return ResponseEntity.ok(translationService.getAll());
    }


    @GetMapping("/id/{translationId}")
    public ResponseEntity<TranslationCreateDto> getTranslation(@PathVariable("translationId") Long translationId) throws TranslationNotFoundException {
        return new ResponseEntity<>(translationService.getTranslation(translationId), HttpStatus.OK);
    }

    @GetMapping("/name/{translationName}")
    public ResponseEntity<TranslationCreateDto> getTranslationByName(@PathVariable("translationName") String translationName) throws TranslationNotFoundException {
        return new ResponseEntity<>(translationService.getTranslationByName(translationName), HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<TranslationCreateDto> add(@Valid @RequestBody TranslationCreateDto translation) throws TranslationAlreadyExistsException {
        return new ResponseEntity<>(translationService.add(translation), HttpStatus.CREATED);
    }


    @DeleteMapping("/id/{translationId}")
    public ResponseEntity<String> delete(@PathVariable ("translationId") Long translationId) throws TranslationNotFoundException {
        translationService.delete(translationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
