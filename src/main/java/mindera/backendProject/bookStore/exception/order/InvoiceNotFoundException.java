package mindera.backendProject.bookStore.exception.order;

public class InvoiceNotFoundException extends OrderException{
    public InvoiceNotFoundException(String message) {
        super(message);
    }
}
