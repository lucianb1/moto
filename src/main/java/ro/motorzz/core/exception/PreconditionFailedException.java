package ro.motorzz.core.exception;

public class PreconditionFailedException extends RuntimeException {

    private static final long serialVersionUID = 57489654585331968L;

    public PreconditionFailedException(String message) {
        super(message);
    }
}
