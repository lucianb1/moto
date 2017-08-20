package ro.motorzz.core.exception;

/**
 * Created by Luci on 26-May-17.
 */
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 49456184121968L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

}
