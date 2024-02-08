package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.PublisherCreateDto;
import mindera.backendProject.bookStore.exception.book.PublisherAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.PublisherNotFoundException;

import java.util.List;

public interface PublisherService {
    List<PublisherCreateDto> getAll(int page, int size, String searchTerm);

    PublisherCreateDto getPublisher(Long publisherId) throws PublisherNotFoundException;

    PublisherCreateDto add(PublisherCreateDto publisher) throws PublisherAlreadyExistsException;

    void delete(Long publisherId) throws PublisherNotFoundException;

    PublisherCreateDto getPublisherByName(String publisherName) throws PublisherNotFoundException;
}
