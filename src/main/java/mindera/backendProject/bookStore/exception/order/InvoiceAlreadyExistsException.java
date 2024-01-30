package mindera.backendProject.bookStore.exception.order;

public class InvoiceAlreadyExistsException extends OrderException{
    public InvoiceAlreadyExistsException(String message) {
        super(message);
    }
}
