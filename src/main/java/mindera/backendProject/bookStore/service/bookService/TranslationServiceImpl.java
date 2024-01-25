package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.TranslationConverter;
import mindera.backendProject.bookStore.dto.book.TranslationCreateDto;
import mindera.backendProject.bookStore.exception.TranslationAlreadyExistsException;
import mindera.backendProject.bookStore.exception.TranslationNotFoundException;
import mindera.backendProject.bookStore.model.Genre;
import mindera.backendProject.bookStore.model.Translation;
import mindera.backendProject.bookStore.repository.bookRepository.TranslationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class TranslationServiceImpl implements TranslationService{

    private final TranslationRepository translationRepository;

    public TranslationServiceImpl(TranslationRepository translationRepository){
        this.translationRepository = translationRepository;
    }

    @Override
    public List<TranslationCreateDto> getAll() {
        List<Translation> translationList = translationRepository.findAll();
        return translationList.stream().map(TranslationConverter::fromModelToTranslationCreateDto).toList();
    }

    @Override
    public TranslationCreateDto getTranslation(Long translationId) throws TranslationNotFoundException {
        Optional<Translation> translationOptional = translationRepository.findById(translationId);
        if(translationOptional.isEmpty()){
            throw new TranslationNotFoundException(TRANSLATION_WITH_ID + translationId + DOESNT_EXIST);
        }
        return TranslationConverter.fromModelToTranslationCreateDto(translationOptional.get());
    }

    public List<Translation> getAllTranslations() {
        return translationRepository.findAll();
    }

    @Override
    public TranslationCreateDto add(TranslationCreateDto translation) throws TranslationAlreadyExistsException {
        Optional<Translation> translationOptional = translationRepository.findByName(translation.name());
       if(translationOptional.isPresent()){
           throw new TranslationAlreadyExistsException(TRANSLATION_ALREADY_EXISTS);
       }
       translationRepository.save(TranslationConverter.fromCreateDtoToModel(translation));
       return translation;
    }

    @Override
    public void delete(Long translationId) throws TranslationNotFoundException {
        Optional<Translation> translationOptional = translationRepository.findById(translationId);
        if(translationOptional.isEmpty()){
            throw new TranslationNotFoundException(TRANSLATION_WITH_ID + translationId + DOESNT_EXIST);
        }
        translationRepository.delete(translationOptional.get());
    }

    @Override
    public TranslationCreateDto getTranslationByName(String translationName) throws TranslationNotFoundException {
       Optional<Translation> translationOptional = translationRepository.findByName(translationName);
        if(translationOptional.isEmpty()){
            throw new TranslationNotFoundException(TRANSLATION_WITH_NAME + translationName + DOESNT_EXIST);
        }
        return TranslationConverter.fromModelToTranslationCreateDto(translationOptional.get());
    }
}
