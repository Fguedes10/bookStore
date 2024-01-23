package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dtos.books.GenreCreateDto;

import java.util.List;

public interface GenreService {


     List<GenreCreateDto> getAll();

    GenreCreateDto getGenre(Long genreId);

    GenreCreateDto add(GenreCreateDto genre);

    void delete(Long id);

    GenreCreateDto getGenreByName(String genreName);
}
