package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dtos.books.LanguageCreateDto;

import java.util.List;

public interface LanguageService {
    List<LanguageCreateDto> getAll();

    LanguageCreateDto getLanguage(Long languageId);

    LanguageCreateDto add(LanguageCreateDto language);

    void delete(Long id);

    LanguageCreateDto getLanguageByName(String languageName);
}
