package mindera.backendProject.bookStore.exception;

public class CustomerAlreadyExistsException extends CustomerException{
    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
