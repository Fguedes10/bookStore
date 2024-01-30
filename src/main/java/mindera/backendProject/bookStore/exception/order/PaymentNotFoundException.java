package mindera.backendProject.bookStore.exception.order;

public class PaymentNotFoundException extends OrderException{
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
