package co.com.sofka.crud.businessexceptions;

public class NotFoundGroupIdException extends RuntimeException{
    public NotFoundGroupIdException(String message) {
        super(message);
    }
}
