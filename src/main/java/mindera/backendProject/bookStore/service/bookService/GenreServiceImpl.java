package mindera.backendProject.bookStore.service.bookService;


import mindera.backendProject.bookStore.converter.GenreConverter;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.exception.GenreAlreadyExistsException;
import mindera.backendProject.bookStore.exception.GenreNotFoundException;
import mindera.backendProject.bookStore.model.Genre;
import mindera.backendProject.bookStore.repository.bookRepository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository){this.genreRepository=genreRepository;}

    @Override
    public List<GenreCreateDto> getAll() {
        List<Genre> genreList = genreRepository.findAll();
        return genreList.stream().map(GenreConverter::fromModelToGenreCreateDto).toList();
    }

    @Override
    public GenreCreateDto getGenre(Long genreId) throws GenreNotFoundException {
        Optional<Genre> genreOptional = genreRepository.findById(genreId);
        if(genreOptional.isEmpty()){
            throw new GenreNotFoundException("Genre with id" + genreId + "does not exist");
        }
        return GenreConverter.fromModelToGenreCreateDto(genreOptional.get());
    }

    @Override
    public GenreCreateDto add(GenreCreateDto genre) throws GenreAlreadyExistsException {
        Optional<Genre> genreOptional = genreRepository.findByName(genre.name());
        if(genreOptional.isPresent()){
            throw new GenreAlreadyExistsException("Genre already exists");
        }
        Genre newGenre =  GenreConverter.fromCreateDtoToModel(genre);
        genreRepository.save(newGenre);
        return genre;
    }

    @Override
    public void delete(Long genreId) throws GenreNotFoundException {
        Optional<Genre> genreOptional = genreRepository.findById(genreId);
        if(genreOptional.isEmpty()){
            throw new GenreNotFoundException("Genre with id" + genreId + "does not exist");
        }
       genreRepository.delete(genreOptional.get());
    }

    @Override
    public GenreCreateDto getGenreByName(String genreName) throws GenreNotFoundException {
        Optional<Genre> genreOptional = genreRepository.findByName(genreName);
        if(genreOptional.isEmpty()){
            throw new GenreNotFoundException("Genre with name" + genreName + "does not exist");
        }
        return GenreConverter.fromModelToGenreCreateDto(genreOptional.get());
    }
}
