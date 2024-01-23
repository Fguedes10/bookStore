package mindera.backendProject.bookStore.service.bookService;


import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{
    @Override
    public List<GenreCreateDto> getAll() {
        return null;
    }

    @Override
    public GenreCreateDto getGenre(Long genreId) {
        return null;
    }

    @Override
    public GenreCreateDto add(GenreCreateDto genre) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public GenreCreateDto getGenreByName(String genreName) {
        return null;
    }
}
