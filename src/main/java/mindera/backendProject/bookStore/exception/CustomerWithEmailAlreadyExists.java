package mindera.backendProject.bookStore.exception;

public class CustomerWithEmailAlreadyExists extends CustomerException{
    public CustomerWithEmailAlreadyExists(String message) {
        super(message);
    }
}
