package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.book.AuthorConverter;
import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;
import mindera.backendProject.bookStore.exception.book.AuthorAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.AuthorNotFoundException;
import mindera.backendProject.bookStore.exception.book.CannotDeleteException;
import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.repository.bookRepository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class AuthorServiceImpl implements AuthorService{


    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorCreateDto> getAll(int page, int size, String searchTerm) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, searchTerm);
        Page<Author> authorList = authorRepository.findAll(pageRequest);
        return authorList.stream().map(AuthorConverter::fromModelToAuthorCreateDto).toList();
    }

    @Override
    public AuthorCreateDto getAuthor(Long authorId) throws AuthorNotFoundException {
        Optional<Author> authorOptional = authorRepository.findById(authorId);
        if(authorOptional.isEmpty()){
            throw new AuthorNotFoundException(AUTHOR_WITH_ID + authorId + DOESNT_EXIST);
        }
        return AuthorConverter.fromModelToAuthorCreateDto(authorOptional.get());
    }

    @Override
    public AuthorCreateDto add(AuthorCreateDto author) throws AuthorAlreadyExistsException {
        Optional<Author> authorOptional = authorRepository.findByName(author.name());
        if (authorOptional.isPresent()) {
            throw new AuthorAlreadyExistsException(AUTHOR_ALREADY_EXISTS);
        }
        authorRepository.save(AuthorConverter.fromCreateDtoToModel(author));
        return author;
    }
    public List<AuthorCreateDto> addMultipleAuthors(List<AuthorCreateDto> authors) throws AuthorAlreadyExistsException {
        List<AuthorCreateDto> authorsCreated = new ArrayList<>();
        for (AuthorCreateDto author : authors) {
            Optional<Author> authorOptional = authorRepository.findByName(author.name());
            if (authorOptional.isPresent()) {
                throw new AuthorAlreadyExistsException(AUTHOR_ALREADY_EXISTS);
            }
            authorRepository.save(AuthorConverter.fromCreateDtoToModel(author));
            authorsCreated.add(author);
        }
        return authorsCreated;
    }

    @Override
    public void delete(Long authorId) throws AuthorNotFoundException, DataIntegrityViolationException, CannotDeleteException {
        try {
            Optional<Author> authorOptional = authorRepository.findById(authorId);

            if (authorOptional.isEmpty()) {
                throw new AuthorNotFoundException(AUTHOR_WITH_ID + authorId + DOESNT_EXIST);
            }
            authorRepository.delete(authorOptional.get());
        } catch (DataIntegrityViolationException exception) {
            throw new CannotDeleteException(CANNOT_BE_DELETED);
        }
    }


    @Override
    public AuthorCreateDto getAuthorByName(String authorName) throws AuthorNotFoundException {
        Optional<Author> authorOptional = authorRepository.findByName(authorName);
        if(authorOptional.isEmpty()){
            throw new AuthorNotFoundException(AUTHOR_WITH_NAME + authorName + DOESNT_EXIST);
        }
        return AuthorConverter.fromModelToAuthorCreateDto(authorOptional.get());
    }

    public Author getAuthorById(Long authorId) throws AuthorNotFoundException {
        Optional<Author> authorOptional = authorRepository.findById(authorId);
        if(authorOptional.isEmpty()){
            throw new AuthorNotFoundException(AUTHOR_WITH_ID + authorId + DOESNT_EXIST);
        }
        return authorOptional.get();
    }
}
