package mindera.backendProject.bookStore.exception.book;

public class BookNotFoundException extends BookException{
    public BookNotFoundException(String message) {
        super(message);
    }
}
