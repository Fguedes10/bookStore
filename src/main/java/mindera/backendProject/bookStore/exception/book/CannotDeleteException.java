package mindera.backendProject.bookStore.exception.book;

import mindera.backendProject.bookStore.exception.book.BookException;

public class CannotDeleteException extends BookException {
    public CannotDeleteException(String message) {
        super(message);
    }

}
