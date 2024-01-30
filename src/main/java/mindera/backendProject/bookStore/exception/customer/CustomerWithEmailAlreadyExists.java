package mindera.backendProject.bookStore.exception.customer;

public class CustomerWithEmailAlreadyExists extends CustomerException{
    public CustomerWithEmailAlreadyExists(String message) {
        super(message);
    }
}
