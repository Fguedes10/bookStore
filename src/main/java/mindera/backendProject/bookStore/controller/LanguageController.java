package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Parameter;

import mindera.backendProject.bookStore.dto.book.LanguageCreateDto;

import mindera.backendProject.bookStore.exception.LanguageAlreadyExistsException;
import mindera.backendProject.bookStore.exception.LanguageNotFoundException;
import mindera.backendProject.bookStore.service.bookService.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/language")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService){
        this.languageService = languageService;
    }


    @GetMapping("/")
    public ResponseEntity<List<LanguageCreateDto>> getLanguages(){
        return ResponseEntity.ok(languageService.getAll());
    }


    @GetMapping("/{languageId}")
    public ResponseEntity<LanguageCreateDto> getLanguage(@PathVariable("languageId") Long languageId) throws LanguageNotFoundException{
        return new ResponseEntity<>(languageService.getLanguage(languageId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<LanguageCreateDto> getLanguageByName(@PathVariable("languageName") String languageName) throws LanguageNotFoundException {
        return new ResponseEntity<>(languageService.getLanguageByName(languageName), HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<LanguageCreateDto> add(@RequestBody LanguageCreateDto language) throws LanguageAlreadyExistsException {
        LanguageCreateDto languageDto = languageService.add(language);
        return new ResponseEntity<>(languageDto, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name= "id", description = "Language id", example = "1") Long id) throws LanguageNotFoundException {
        languageService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
