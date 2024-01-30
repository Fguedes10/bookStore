package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.TranslationCreateDto;
import mindera.backendProject.bookStore.exception.book.TranslationAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.TranslationNotFoundException;
import mindera.backendProject.bookStore.service.bookService.TranslationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/translations")
public class TranslationController {


    private final TranslationServiceImpl translationService;


    public TranslationController(TranslationServiceImpl translationService){
        this.translationService = translationService;
    }

    @Operation(
            summary = "Get all existing translations",
            description = "Get all existing translations"
    )
    @GetMapping("/")
    public ResponseEntity<List<TranslationCreateDto>> getTranslation(){
        return ResponseEntity.ok(translationService.getAll());
    }


    @Operation(
            summary = "Get translation by id",
            description = "Get translation by id"
    )
    @GetMapping("/id/{translationId}")
    public ResponseEntity<TranslationCreateDto> getTranslation(@PathVariable("translationId")@Parameter(name = "Translation Id", description = "Translation id", example = "1") Long translationId) throws TranslationNotFoundException {
        return new ResponseEntity<>(translationService.getTranslation(translationId), HttpStatus.OK);
    }


    @Operation(
            summary = "Get translation by name",
            description = "Get translation by name"
    )
    @GetMapping("/name/{translationName}")
    public ResponseEntity<TranslationCreateDto> getTranslationByName(@PathVariable("translationName")@Parameter(name = "Translation Name", description = "Translation name", example = "English") String translationName) throws TranslationNotFoundException {
        return new ResponseEntity<>(translationService.getTranslationByName(translationName), HttpStatus.OK);
    }


    @Operation(
            summary = "Add new translation",
            description = "Add new translation"
    )
    @PostMapping("/")
    public ResponseEntity<TranslationCreateDto> add(@Valid @RequestBody TranslationCreateDto translation) throws TranslationAlreadyExistsException {
        return new ResponseEntity<>(translationService.add(translation), HttpStatus.CREATED);
    }



    @Operation(
            summary = "Add multiple translations",
            description = "Add multiple translations"
    )
    @PostMapping("/addMultipleTranslations")
    public ResponseEntity<List<TranslationCreateDto>> addMultipleTranslations(@Valid @RequestBody List<TranslationCreateDto> translations) throws TranslationAlreadyExistsException {
        return new ResponseEntity<>(translationService.addMultipleTranslations(translations), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Delete translation by id",
            description = "Delete translation by id"
    )
    @DeleteMapping("/id/{translationId}")
    public ResponseEntity<String> delete(@PathVariable ("translationId")@Parameter(name = "Translation Id", description = "Translation id", example = "1") Long translationId) throws TranslationNotFoundException {
        translationService.delete(translationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
