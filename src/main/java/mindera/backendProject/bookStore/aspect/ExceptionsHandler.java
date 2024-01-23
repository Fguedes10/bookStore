package mindera.backendProject.bookStore.aspect;

import mindera.backendProject.bookStore.exception.CustomerAlreadyExistsException;
import mindera.backendProject.bookStore.exception.CustomerNotFoundException;
import mindera.backendProject.bookStore.exception.CustomerWithEmailAlreadyExists;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Aspect
@ControllerAdvice
public class ExceptionsHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(value = {CustomerNotFoundException.class})
    public ResponseEntity<String> NotFoundHandler (Exception exception){
        logger.error("Know exception: " + exception.getMessage());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
    @ExceptionHandler(value = {CustomerAlreadyExistsException.class})
    public ResponseEntity<String> AlreadyExistsHandler (Exception exception){
        logger.error("Know exception: " + exception.getMessage());
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(value = {CustomerWithEmailAlreadyExists.class})
    public ResponseEntity<String> EmailAlreadyExistsHandler (Exception exception){
        logger.error("Know exception: " + exception.getMessage());
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }



}
