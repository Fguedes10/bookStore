package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.AuthorConverter;
import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;
import mindera.backendProject.bookStore.exception.AuthorAlreadyExistsException;
import mindera.backendProject.bookStore.exception.AuthorNotFoundException;
import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.repository.bookRepository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    @Autowired
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
    public AuthorCreateDto add(AuthorCreateDto author) throws AuthorAlreadyExistsException {
        if (authorRepository.findByName(author.name()).isPresent()) {
            throw new AuthorAlreadyExistsException("This author already exists");
        }
            Author newAuthor = authorRepository.save(AuthorConverter.fromCreateDtoToModel(author));
            return author;
    }

    @Override
    public void delete(Long authorId) throws AuthorNotFoundException {
        authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException("Author with id" + authorId + "does not exists"));
        authorRepository.deleteById(authorId);
    }

    @Override
    public AuthorCreateDto getAuthorByName(String authorName) throws AuthorNotFoundException {
        return (AuthorCreateDto) authorRepository.findByName(authorName).orElseThrow(()-> new AuthorNotFoundException("Author with name" + authorName + "does not exists"));
    }
}
