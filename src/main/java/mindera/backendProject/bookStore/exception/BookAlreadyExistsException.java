package mindera.backendProject.bookStore.exception;

public class BookAlreadyExistsException extends BookException{
    public BookAlreadyExistsException(String message) {
        super(message);
    }
}
