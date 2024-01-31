package mindera.backendProject.bookStore.exception.order;

public class OrderNotFoundException extends OrderException{
    public OrderNotFoundException(String message) {
        super(message);
    }
}
