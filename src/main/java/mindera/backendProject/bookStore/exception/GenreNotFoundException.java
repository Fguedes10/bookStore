package mindera.backendProject.bookStore.exception;

public class GenreNotFoundException extends BookException{
    public GenreNotFoundException(String message) {
        super(message);
    }
}
