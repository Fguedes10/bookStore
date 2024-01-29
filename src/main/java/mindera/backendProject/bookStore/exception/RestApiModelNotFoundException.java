package mindera.backendProject.bookStore.exception;

public class RestApiModelNotFoundException extends RuntimeException{

    public RestApiModelNotFoundException(){
        super("Google Book not found");
    }
}
