package exceptions;

public class PersonExistsException extends Exception{
    public PersonExistsException(String message) {
        super(message);
    }
    public PersonExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
