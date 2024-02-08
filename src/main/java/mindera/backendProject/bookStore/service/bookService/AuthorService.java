package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;
import mindera.backendProject.bookStore.exception.book.AuthorAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.AuthorNotFoundException;
import mindera.backendProject.bookStore.exception.book.CannotDeleteException;

import java.util.List;


public interface AuthorService {


    List<AuthorCreateDto> getAll(int page, int size, String searchTerm);
    AuthorCreateDto getAuthor(Long authorId) throws AuthorNotFoundException;

    AuthorCreateDto add(AuthorCreateDto author) throws AuthorAlreadyExistsException;

    void delete(Long id) throws AuthorNotFoundException, CannotDeleteException;

    AuthorCreateDto getAuthorByName(String authorName) throws AuthorNotFoundException;
}
