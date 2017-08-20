package ro.motorzz.core.exception;

/**
 * Created by bjz on 6/30/2017.
 */
public class UnsupportedTypeException extends RuntimeException {
    public UnsupportedTypeException() {
    }

    public UnsupportedTypeException(String message) {
        super(message);
    }
}
