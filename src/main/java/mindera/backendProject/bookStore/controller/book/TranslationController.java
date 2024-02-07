package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.TranslationCreateDto;
import mindera.backendProject.bookStore.exception.book.TranslationAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.TranslationNotFoundException;
import mindera.backendProject.bookStore.service.bookService.TranslationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/translations")
public class TranslationController {


    @Autowired
    private TranslationServiceImpl translationService;


    @Operation(
            summary = "Get all existing translations",
            description = "Get all existing translations"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Translations found")})
    @GetMapping("/")
    public ResponseEntity<List<TranslationCreateDto>> getTranslation(){
        return ResponseEntity.ok(translationService.getAll());
    }


    @Operation(
            summary = "Get translation by id",
            description = "Get translation by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Translation found"),
            @ApiResponse(responseCode = "404", description = "Translation not found")})
    @GetMapping("/id/{translationId}")
    public ResponseEntity<TranslationCreateDto> getTranslation(@PathVariable("translationId")@Parameter(name = "Translation Id", description = "Translation id", example = "1") Long translationId) throws TranslationNotFoundException {
        return new ResponseEntity<>(translationService.getTranslation(translationId), HttpStatus.OK);
    }


    @Operation(
            summary = "Get translation by name",
            description = "Get translation by name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Translation found"),
            @ApiResponse(responseCode = "404", description = "Translation not found")})
    @GetMapping("/name/{translationName}")
    public ResponseEntity<TranslationCreateDto> getTranslationByName(@PathVariable("translationName")@Parameter(name = "Translation Name", description = "Translation name", example = "English") String translationName) throws TranslationNotFoundException {
        return new ResponseEntity<>(translationService.getTranslationByName(translationName), HttpStatus.OK);
    }


    @Operation(
            summary = "Add new translation",
            description = "Add new translation"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Translation created"),
            @ApiResponse(responseCode = "409", description = "Translation already exists")})
    @PostMapping("/")
    public ResponseEntity<TranslationCreateDto> add(@Valid @RequestBody TranslationCreateDto translation) throws TranslationAlreadyExistsException {
        return new ResponseEntity<>(translationService.add(translation), HttpStatus.CREATED);
    }



    @Operation(
            summary = "Add multiple translations",
            description = "Add multiple translations"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Translations created"),
            @ApiResponse(responseCode = "409", description = "Translations already exists")})
    @PostMapping("/addMultipleTranslations")
    public ResponseEntity<List<TranslationCreateDto>> addMultipleTranslations(@Valid @RequestBody List<TranslationCreateDto> translations) throws TranslationAlreadyExistsException {
        return new ResponseEntity<>(translationService.addMultipleTranslations(translations), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Delete translation by id",
            description = "Delete translation by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Translation deleted"),
            @ApiResponse(responseCode = "404", description = "Translation not found")})
    @DeleteMapping("/id/{translationId}")
    public ResponseEntity<String> delete(@PathVariable ("translationId")@Parameter(name = "Translation Id", description = "Translation id", example = "1") Long translationId) throws TranslationNotFoundException {
        translationService.delete(translationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
