package ro.motorzz.core.exception;

public class ConflictException extends RuntimeException {

    private static final long serialVersionUID = 54447954588764268L;

    public ConflictException(String message) {
        super(message);
    }
}