package mindera.backendProject.bookStore.exception.order;

public class PaymentAlreadyExistsException extends OrderException{
    public PaymentAlreadyExistsException(String message) {
        super(message);
    }
}
