package mindera.backendProject.bookStore.exception.book;

public class ReviewNotFoundException extends BookException{
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
