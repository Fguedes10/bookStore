package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.LanguageCreateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService{
    @Override
    public List<LanguageCreateDto> getAll() {
        return null;
    }

    @Override
    public LanguageCreateDto getLanguage(Long languageId) {
        return null;
    }

    @Override
    public LanguageCreateDto add(LanguageCreateDto language) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public LanguageCreateDto getLanguageByName(String languageName) {
        return null;
    }
}
