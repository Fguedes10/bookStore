package mindera.backendProject.bookStore.exception.book;

public class AuthorNotFoundException extends BookException{
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
