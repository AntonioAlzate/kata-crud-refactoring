package co.com.sofka.crud.businessexceptions;

public class EmptyFieldException extends RuntimeException{

    public EmptyFieldException(String message) {
        super(message);
    }
}
