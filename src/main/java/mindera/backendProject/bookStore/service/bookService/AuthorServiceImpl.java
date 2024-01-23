package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.AuthorConverter;
import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;
import mindera.backendProject.bookStore.exception.AuthorAlreadyExistsException;
import mindera.backendProject.bookStore.exception.AuthorNotFoundException;
import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.repository.bookRepository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository=authorRepository;}


    @Override
    public List<AuthorCreateDto> getAll() {
        List<Author> authorList = authorRepository.findAll();
        return authorList.stream().map(AuthorConverter::fromModelToAuthorCreateDto).toList();
    }

    @Override
    public AuthorCreateDto getAuthor(Long authorId) throws AuthorNotFoundException {
        Optional<Author> authorOptional = authorRepository.findById(authorId);
        if(authorOptional.isEmpty()){
            throw new AuthorNotFoundException("Author with id: " + authorId + "does not exists");
        }
        return AuthorConverter.fromModelToAuthorCreateDto(authorOptional.get());
    }

    @Override
    public AuthorCreateDto add(AuthorCreateDto author)  {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public AuthorCreateDto getAuthorByName(String authorName) {
        return null;
    }
}
