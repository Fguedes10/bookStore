package mindera.backendProject.bookStore.exception.order;

public class OrderAlreadyExistsException extends OrderException{
    public OrderAlreadyExistsException(String message) {
        super(message);
    }
}
