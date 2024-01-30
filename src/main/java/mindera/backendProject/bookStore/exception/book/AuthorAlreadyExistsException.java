package mindera.backendProject.bookStore.exception.book;

public class AuthorAlreadyExistsException extends BookException{
    public AuthorAlreadyExistsException(String message) {
        super(message);
    }
}
