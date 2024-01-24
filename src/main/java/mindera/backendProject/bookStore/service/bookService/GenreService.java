package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.exception.GenreAlreadyExistsException;
import mindera.backendProject.bookStore.exception.GenreNotFoundException;

import java.util.List;

public interface GenreService {


     List<GenreCreateDto> getAll();

    GenreCreateDto getGenre(Long genreId) throws GenreNotFoundException;

    GenreCreateDto add(GenreCreateDto genre) throws GenreAlreadyExistsException;

    void delete(Long id) throws GenreNotFoundException;

    GenreCreateDto getGenreByName(String genreName) throws GenreNotFoundException;
}
