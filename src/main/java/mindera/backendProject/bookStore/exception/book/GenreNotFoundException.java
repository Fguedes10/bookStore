package mindera.backendProject.bookStore.exception.book;

public class GenreNotFoundException extends BookException{
    public GenreNotFoundException(String message) {
        super(message);
    }
}
