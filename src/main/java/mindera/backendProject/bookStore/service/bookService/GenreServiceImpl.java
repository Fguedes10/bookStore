package mindera.backendProject.bookStore.service.bookService;


import mindera.backendProject.bookStore.converter.book.GenreConverter;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.exception.book.GenreAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.GenreNotFoundException;
import mindera.backendProject.bookStore.model.Genre;
import mindera.backendProject.bookStore.repository.bookRepository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class GenreServiceImpl implements GenreService{


    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<GenreCreateDto> getAll() {
        List<Genre> genreList = genreRepository.findAll();
        return genreList.stream().map(GenreConverter::fromModelToGenreCreateDto).toList();
    }

    @Override
    public GenreCreateDto getGenre(Long genreId) throws GenreNotFoundException {
        Optional<Genre> genreOptional = genreRepository.findById(genreId);
        if(genreOptional.isEmpty()){
            throw new GenreNotFoundException(GENRE_WITH_ID  + genreId + DOESNT_EXIST);
        }
        return GenreConverter.fromModelToGenreCreateDto(genreOptional.get());
    }

    public List<Genre> getAllGenres() {
       return genreRepository.findAll();
    }

    @Override
    public GenreCreateDto add(GenreCreateDto genre) throws GenreAlreadyExistsException {
        Optional<Genre> genreOptional = genreRepository.findByName(genre.name());
        if(genreOptional.isPresent()){
            throw new GenreAlreadyExistsException(GENRE_ALREADY_EXISTS);
        }
        Genre newGenre =  GenreConverter.fromCreateDtoToModel(genre);
        genreRepository.save(newGenre);
        return genre;
    }

    public List<GenreCreateDto> addMultipleGenres(List<GenreCreateDto> genres) throws GenreAlreadyExistsException {
        List<GenreCreateDto> genresCreated = new ArrayList<>();
        for (GenreCreateDto genre : genres) {
            Optional<Genre> genreOptional = genreRepository.findByName(genre.name());
            if(genreOptional.isPresent()){
                throw new GenreAlreadyExistsException(GENRE_ALREADY_EXISTS);
            }
            Genre newGenre =  GenreConverter.fromCreateDtoToModel(genre);
            genreRepository.save(newGenre);
            genresCreated.add(genre);
        }
        return genresCreated;
    }



    @Override
    public void delete(Long genreId) throws GenreNotFoundException {
        Optional<Genre> genreOptional = genreRepository.findById(genreId);
        if(genreOptional.isEmpty()){
            throw new GenreNotFoundException(GENRE_WITH_ID  + genreId + DOESNT_EXIST);
        }
       genreRepository.delete(genreOptional.get());
    }

    @Override
    public GenreCreateDto getGenreByName(String genreName) throws GenreNotFoundException {
        Optional<Genre> genreOptional = genreRepository.findByName(genreName);
        if(genreOptional.isEmpty()){
            throw new GenreNotFoundException(GENRE_WITH_NAME + genreName + DOESNT_EXIST);
        }
        return GenreConverter.fromModelToGenreCreateDto(genreOptional.get());
    }

    public List<Genre> findByIds(List<Long> genreIds) throws GenreNotFoundException {
        List<Genre> genreList = genreRepository.findAllByIdIn(genreIds);
        Set<Long> existingIds = genreList.stream().map(Genre::getId).collect(Collectors.toSet());
        List<Long> nonExistingIds = genreIds.stream().filter(id -> !existingIds.contains(id)).toList();
        if(!nonExistingIds.isEmpty()){
            throw new GenreNotFoundException("Genre with the Id/s: " + nonExistingIds + " doesn't exist/s. ");
        }
        return genreList;
    }
}
