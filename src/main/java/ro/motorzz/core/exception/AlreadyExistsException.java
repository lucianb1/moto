package ro.motorzz.core.exception;

/**
 * Created by Luci on 26-May-17.
 */
public class AlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 45267794585331968L;

    public AlreadyExistsException(String message) {
        super(message);
    }
}
