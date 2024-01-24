package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.LanguageCreateDto;
import mindera.backendProject.bookStore.exception.LanguageAlreadyExistsException;
import mindera.backendProject.bookStore.exception.LanguageNotFoundException;

import java.util.List;

public interface LanguageService {
    List<LanguageCreateDto> getAll();

    LanguageCreateDto getLanguage(Long languageId) throws LanguageNotFoundException;

    LanguageCreateDto add(LanguageCreateDto language) throws LanguageAlreadyExistsException;

    void delete(Long id) throws LanguageNotFoundException;

    LanguageCreateDto getLanguageByName(String languageName) throws LanguageNotFoundException;
}
