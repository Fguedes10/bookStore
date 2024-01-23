package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dtos.books.TranslationCreateDto;

import java.util.List;

public interface TranslationService {
    List<TranslationCreateDto> getAll();

    TranslationCreateDto getTranslation(Long translationId);

    TranslationCreateDto add(TranslationCreateDto translation);

    void delete(Long id);

    TranslationCreateDto getTranslationByName(String translationName);
}
