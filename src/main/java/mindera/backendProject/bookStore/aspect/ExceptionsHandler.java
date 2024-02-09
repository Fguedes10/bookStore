package mindera.backendProject.bookStore.aspect;

import mindera.backendProject.bookStore.exception.book.*;
import mindera.backendProject.bookStore.exception.customer.CustomerAlreadyExistsException;
import mindera.backendProject.bookStore.exception.customer.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.customer.CustomerRepeatedFavoriteBooks;
import mindera.backendProject.bookStore.exception.customer.CustomerWithEmailAlreadyExists;
import mindera.backendProject.bookStore.exception.order.*;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Aspect
@ControllerAdvice
public class ExceptionsHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(value = {CustomerNotFoundException.class, AuthorNotFoundException.class,
            BookNotFoundException.class, GenreNotFoundException.class, RatingNotFoundException.class,
            ReviewNotFoundException.class, TranslationNotFoundException.class, DownloadNotFoundException.class,
            InvoiceNotFoundException.class, OrderNotFoundException.class, OrderItemNotFoundException.class,
            PaymentNotFoundException.class, OrderItemNotFoundException.class, PdfNotFoundException.class})
    public ResponseEntity<String> NotFoundHandler(Exception exception) {
        logger.error("Know exception: " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = {CustomerAlreadyExistsException.class, AuthorAlreadyExistsException.class, BookAlreadyExistsException.class, GenreAlreadyExistsException.class,
            TranslationAlreadyExistsException.class, CustomerWithEmailAlreadyExists.class, IncorrectReleaseYearException.class,
            CustomerRepeatedFavoriteBooks.class, DownloadAlreadyExistsException.class, PublisherAlreadyExistsException.class, InvoiceAlreadyExistsException.class,
            OrderAlreadyExistsException.class, PaymentAlreadyExistsException.class, OrderAlreadyExistsException.class})
    public ResponseEntity<String> AlreadyExistsHandler(Exception exception) {
        logger.error("Know exception: " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> validationsHandlerNotValid(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return ResponseEntity.badRequest().body(errors);
    }

/*    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<String> generalHandler(Exception exception) {
        logger.error("Know exception: " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }*/


}
