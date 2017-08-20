package ro.motorzz.core.exception;

/**
 * Created by Luci on 26-May-17.
 */
public class InvalidArgumentException extends RuntimeException {
    private static final long serialVersionUID = -4526756184121968L;

    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(Throwable t) {super(t);}
}
