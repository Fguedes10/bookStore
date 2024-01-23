package mindera.backendProject.bookStore.exception;

public class AuthorAlreadyExistsException extends BookException{
    public AuthorAlreadyExistsException(String message) {
        super(message);
    }
}
