package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;
import mindera.backendProject.bookStore.exception.AuthorAlreadyExistsException;
import mindera.backendProject.bookStore.exception.AuthorNotFoundException;
import mindera.backendProject.bookStore.exception.CannotDeleteException;

import java.util.List;


public interface AuthorService {


    List<AuthorCreateDto> getAll();
    AuthorCreateDto getAuthor(Long authorId) throws AuthorNotFoundException;

    AuthorCreateDto add(AuthorCreateDto author) throws AuthorAlreadyExistsException;

    void delete(Long id) throws AuthorNotFoundException, CannotDeleteException;

    AuthorCreateDto getAuthorByName(String authorName) throws AuthorNotFoundException;
}
