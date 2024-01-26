package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.PublisherCreateDto;
import mindera.backendProject.bookStore.exception.PublisherAlreadyExistsException;
import mindera.backendProject.bookStore.exception.PublisherNotFoundException;

import java.util.List;

public interface PublisherService {
    List<PublisherCreateDto> getAll();

    PublisherCreateDto getPublisher(Long publisherId) throws PublisherNotFoundException;

    PublisherCreateDto add(PublisherCreateDto publisher) throws PublisherAlreadyExistsException;

    void delete(Long publisherId) throws PublisherNotFoundException;

    PublisherCreateDto getPublisherByName(String publisherName) throws PublisherNotFoundException;
}