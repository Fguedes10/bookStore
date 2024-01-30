package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.book.PublisherConverter;
import mindera.backendProject.bookStore.dto.book.PublisherCreateDto;
import mindera.backendProject.bookStore.exception.book.PublisherAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.PublisherNotFoundException;
import mindera.backendProject.bookStore.model.Publisher;
import mindera.backendProject.bookStore.repository.bookRepository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class PublisherServiceImpl implements PublisherService{
    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository){
        this.publisherRepository=publisherRepository;}


    @Override
    public List<PublisherCreateDto> getAll() {
        List<Publisher> publisherList = publisherRepository.findAll();
        return publisherList.stream().map(PublisherConverter::fromModelToPublisherCreateDto).toList();
    }

    @Override
    public PublisherCreateDto getPublisher(Long publisherId) throws PublisherNotFoundException {
        Optional<Publisher> publisherOptional = publisherRepository.findById(publisherId);
        if(publisherOptional.isEmpty()){
            throw new PublisherNotFoundException(PUBLISHER_WITH_ID + publisherId + DOESNT_EXIST);
        }
        return PublisherConverter.fromModelToPublisherCreateDto(publisherOptional.get());
    }

    @Override
    public PublisherCreateDto add(PublisherCreateDto publisher) throws PublisherAlreadyExistsException {
        Optional<Publisher> publisherOptional = publisherRepository.findByName(publisher.name());
        if (publisherOptional.isPresent()) {
            throw new PublisherAlreadyExistsException(PUBLISHER_ALREADY_EXISTS);
        }
        publisherRepository.save(PublisherConverter.fromCreateDtoToModel(publisher));
        return publisher;
    }

    @Override
    public void delete(Long publisherId) throws PublisherNotFoundException {
        Optional<Publisher> publisherOptional = publisherRepository.findById(publisherId);
        if(publisherOptional.isEmpty()){
            throw new PublisherNotFoundException(PUBLISHER_WITH_ID + publisherId + DOESNT_EXIST);
        }
        publisherRepository.delete(publisherOptional.get());
    }

    @Override
    public PublisherCreateDto getPublisherByName(String publisherName) throws PublisherNotFoundException {
        Optional<Publisher> publisherOptional = publisherRepository.findByName(publisherName);
        if(publisherOptional.isEmpty()){
            throw new PublisherNotFoundException(PUBLISHER_WITH_NAME + publisherName + DOESNT_EXIST);
        }
        return PublisherConverter.fromModelToPublisherCreateDto(publisherOptional.get());
    }

    public Publisher getPublisherById(Long publisherId) throws PublisherNotFoundException {
        Optional<Publisher> publisherOptional = publisherRepository.findById(publisherId);
        if(publisherOptional.isEmpty()){
            throw new PublisherNotFoundException(PUBLISHER_WITH_ID + publisherId + DOESNT_EXIST);
        }
        return publisherOptional.get();
    }
}
