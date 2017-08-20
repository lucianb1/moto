package ro.motorzz.core.exception;

/**
 * Created by Luci on 26-May-17.
 */
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 4337223543L;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(Throwable t) {
        super(t);
    }

}
