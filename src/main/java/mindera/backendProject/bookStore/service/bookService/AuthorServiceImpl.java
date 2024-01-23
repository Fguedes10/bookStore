package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dtos.books.AuthorCreateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{


    @Override
    public List<AuthorCreateDto> getAll() {
        return null;
    }

    @Override
    public AuthorCreateDto getAuthor(Long authorId) {
        return null;
    }

    @Override
    public AuthorCreateDto add(AuthorCreateDto author) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
