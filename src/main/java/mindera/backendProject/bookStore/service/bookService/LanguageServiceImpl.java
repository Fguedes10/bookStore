package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.LanguageConverter;
import mindera.backendProject.bookStore.dto.book.LanguageCreateDto;
import mindera.backendProject.bookStore.exception.LanguageAlreadyExistsException;
import mindera.backendProject.bookStore.exception.LanguageNotFoundException;
import mindera.backendProject.bookStore.model.Language;
import mindera.backendProject.bookStore.repository.bookRepository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements LanguageService{

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(LanguageRepository languageRepository){
        this.languageRepository = languageRepository;
    }
    @Override
    public List<LanguageCreateDto> getAll() {
        List<Language> languageList = languageRepository.findAll();
        return languageList.stream().map(LanguageConverter::fromModelToLanguageCreateDto).toList();
    }

    @Override
    public LanguageCreateDto getLanguage(Long languageId) throws LanguageNotFoundException {
        Optional<Language> languageOptional = languageRepository.findById(languageId);
        if(languageOptional.isEmpty()){
            throw new LanguageNotFoundException("Language with id" + languageId + "does not exist");
        }
        return LanguageConverter.fromModelToLanguageCreateDto(languageOptional.get());
    }

    @Override
    public LanguageCreateDto add(LanguageCreateDto language) throws LanguageAlreadyExistsException {
        if(languageRepository.findByName(language.name()).isPresent()){
            throw new LanguageAlreadyExistsException("Language already exists");
        }
        Language newLanguage = LanguageConverter.fromCreateDtoToModel(language);
        return(language);
    }

    @Override
    public void delete(Long languageId) throws LanguageNotFoundException {
        languageRepository.findById(languageId).orElseThrow(() -> new LanguageNotFoundException("Language with id" + languageId + "does not exist"));
        languageRepository.deleteById(languageId);
    }

    @Override
    public LanguageCreateDto getLanguageByName(String languageName) throws LanguageNotFoundException {
        return (LanguageCreateDto) languageRepository.findByName(languageName).orElseThrow(() -> new LanguageNotFoundException("Language with name" + languageName + "does not exist"));
    }
}
