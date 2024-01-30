package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.TranslationCreateDto;
import mindera.backendProject.bookStore.exception.book.TranslationAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.TranslationNotFoundException;

import java.util.List;

public interface TranslationService {
    List<TranslationCreateDto> getAll();

    TranslationCreateDto getTranslation(Long translationId) throws TranslationNotFoundException;

    TranslationCreateDto add(TranslationCreateDto translation) throws TranslationAlreadyExistsException;

    void delete(Long id) throws TranslationNotFoundException;

    TranslationCreateDto getTranslationByName(String translationName) throws TranslationNotFoundException;
}
