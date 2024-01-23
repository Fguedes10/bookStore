package mindera.backendProject.bookStore.exception;

public class BookNotFoundException extends BookException{
    public BookNotFoundException(String message) {
        super(message);
    }
}
