package mindera.backendProject.bookStore.exception.book;

public class RatingNotFoundException extends BookException{
    public RatingNotFoundException(String message) {
        super(message);
    }
}
