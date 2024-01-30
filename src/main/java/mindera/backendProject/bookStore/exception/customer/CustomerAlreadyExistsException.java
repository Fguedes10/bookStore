package mindera.backendProject.bookStore.exception.customer;

public class CustomerAlreadyExistsException extends CustomerException{
    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
