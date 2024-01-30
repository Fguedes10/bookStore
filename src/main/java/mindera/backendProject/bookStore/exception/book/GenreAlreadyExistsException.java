package mindera.backendProject.bookStore.exception.book;

public class GenreAlreadyExistsException extends BookException{
    public GenreAlreadyExistsException(String message) {
        super(message);
    }

}
