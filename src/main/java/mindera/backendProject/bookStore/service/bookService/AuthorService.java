package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;

import java.util.List;

public interface AuthorService {


    List<AuthorCreateDto> getAll();
    AuthorCreateDto getAuthor(Long authorId);

    AuthorCreateDto add(AuthorCreateDto author);

    void delete(Long id);

    AuthorCreateDto getAuthorByName(String authorName);
}
