package mindera.backendProject.bookStore.exception.book;

public class BookAlreadyExistsException extends BookException{
    public BookAlreadyExistsException(String message) {
        super(message);
    }
}
