package mindera.backendProject.bookStore.exception.order;

public class OrderItemAlreadyExistsException extends OrderException{
    public OrderItemAlreadyExistsException(String message) {
        super(message);
    }
}
