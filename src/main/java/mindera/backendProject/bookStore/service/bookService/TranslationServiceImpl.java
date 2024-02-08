package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.book.TranslationConverter;
import mindera.backendProject.bookStore.dto.book.TranslationCreateDto;
import mindera.backendProject.bookStore.exception.book.TranslationAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.TranslationNotFoundException;
import mindera.backendProject.bookStore.model.Translation;
import mindera.backendProject.bookStore.repository.bookRepository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class TranslationServiceImpl implements TranslationService{


    private final TranslationRepository translationRepository;

    public TranslationServiceImpl(TranslationRepository translationRepository) {
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

    public List<TranslationCreateDto> addMultipleTranslations(List<TranslationCreateDto> translations) throws TranslationAlreadyExistsException {
        List<TranslationCreateDto> translationsCreated = new ArrayList<>();
        for(TranslationCreateDto translation : translations){
            Optional<Translation> translationOptional = translationRepository.findByName(translation.name());
            if(translationOptional.isPresent()){
                throw new TranslationAlreadyExistsException(TRANSLATION_ALREADY_EXISTS);
            }
            translationRepository.save(TranslationConverter.fromCreateDtoToModel(translation));
            translationsCreated.add(translation);
        }
        return translationsCreated;
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

    public List<Translation> findByIds(List<Long> translationIds) throws TranslationNotFoundException {
        List<Translation> translationList = translationRepository.findAllByIdIn(translationIds);
        Set<Long> existingIds = translationList.stream().map(Translation::getId).collect(Collectors.toSet());
        List<Long> nonExistingIds = translationIds.stream().filter(id -> !existingIds.contains(id)).toList();
        if(!nonExistingIds.isEmpty()){
            throw new TranslationNotFoundException("Translations with the Id/s: " + nonExistingIds + " doesn't exist/s. ");
        }
        return  translationList;
    }
}