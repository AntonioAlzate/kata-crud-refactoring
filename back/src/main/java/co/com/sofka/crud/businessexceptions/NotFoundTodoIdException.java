package co.com.sofka.crud.businessexceptions;

public class NotFoundTodoIdException extends RuntimeException{
    public NotFoundTodoIdException(String message) {
        super(message);
    }
}
