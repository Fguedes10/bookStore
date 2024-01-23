package mindera.backendProject.bookStore.exception;

public class AuthorNotFoundException extends BookException{
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
