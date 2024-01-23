package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.TranslationCreateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslationServiceImpl implements TranslationService{
    @Override
    public List<TranslationCreateDto> getAll() {
        return null;
    }

    @Override
    public TranslationCreateDto getTranslation(Long translationId) {
        return null;
    }

    @Override
    public TranslationCreateDto add(TranslationCreateDto translation) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public TranslationCreateDto getTranslationByName(String translationName) {
        return null;
    }
}
