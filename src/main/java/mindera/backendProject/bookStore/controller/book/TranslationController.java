package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static mindera.backendProject.bookStore.util.Messages.*;

@Tag(name = TRANSLATION_TAG_NAME, description = TRANSLATION_TAG_DESCRIPTION)
@RestController
@RequestMapping("api/v1/translations")
public class TranslationController {


    @Autowired
    private TranslationServiceImpl translationService;


    @Operation(
            summary = GET_ALL_EXIST_TRANSLATIONS,
            description = GET_ALL_EXIST_TRANSLATIONS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = TRANSLATIONS_FOUND)})
    @GetMapping("/")
    public ResponseEntity<List<TranslationCreateDto>> getTranslation(){
        return ResponseEntity.ok(translationService.getAll());
    }


    @Operation(
            summary = GET_TRANSLATION_BY_ID,
            description = GET_TRANSLATION_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = TRANSLATION_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = TRANSLATION_NOT_FOUND)})
    @GetMapping("/id/{translationId}")
    public ResponseEntity<TranslationCreateDto> getTranslation(@PathVariable("translationId")@Parameter(name = "Translation Id", description = "Translation id", example = "1") Long translationId) throws TranslationNotFoundException {
        return new ResponseEntity<>(translationService.getTranslation(translationId), HttpStatus.OK);
    }


    @Operation(
            summary = GET_TRANSLATION_BY_NAME,
            description = GET_TRANSLATION_BY_NAME
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = TRANSLATION_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = TRANSLATION_NOT_FOUND)})
    @GetMapping("/name/{translationName}")
    public ResponseEntity<TranslationCreateDto> getTranslationByName(@PathVariable("translationName")@Parameter(name = "Translation Name", description = "Translation name", example = "English") String translationName) throws TranslationNotFoundException {
        return new ResponseEntity<>(translationService.getTranslationByName(translationName), HttpStatus.OK);
    }


    @Operation(
            summary = ADD_NEW_TRANSLATION,
            description = ADD_NEW_TRANSLATION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = TRANSLATION_CREATED),
            @ApiResponse(responseCode = CONFLICT, description = TRANSLATION_ALREADY_EXISTS)})
    @PostMapping("/")
    public ResponseEntity<TranslationCreateDto> add(@Valid @RequestBody TranslationCreateDto translation) throws TranslationAlreadyExistsException {
        return new ResponseEntity<>(translationService.add(translation), HttpStatus.CREATED);
    }



    @Operation(
            summary = ADD_MULTIPLE_TRANSLATIONS,
            description = ADD_MULTIPLE_TRANSLATIONS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = TRANSLATIONS_CREATED),
            @ApiResponse(responseCode = CONFLICT, description = TRANSLATION_ALREADY_EXISTS)})
    @PostMapping("/addMultipleTranslations")
    public ResponseEntity<List<TranslationCreateDto>> addMultipleTranslations(@Valid @RequestBody List<TranslationCreateDto> translations) throws TranslationAlreadyExistsException {
        return new ResponseEntity<>(translationService.addMultipleTranslations(translations), HttpStatus.CREATED);
    }


    @Operation(
            summary = DELETE_TRANSLATION_BY_ID,
            description = DELETE_TRANSLATION_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = TRANSLATION_DELETED),
            @ApiResponse(responseCode = NOT_FOUND, description = TRANSLATION_NOT_FOUND)})
    @DeleteMapping("/id/{translationId}")
    public ResponseEntity<String> delete(@PathVariable ("translationId")@Parameter(name = "Translation Id", description = "Translation id", example = "1") Long translationId) throws TranslationNotFoundException {
        translationService.delete(translationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
