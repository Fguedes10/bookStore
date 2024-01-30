package mindera.backendProject.bookStore.exception.order;

public class OrderItemNotFoundException extends OrderException{
    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
