package ro.motorzz.core.exception;

/**
 * Created by Luci on 26-May-17.
 */
public class PermissionDeniedException extends RuntimeException {
    private static final long serialVersionUID = 45267794561848L;

    public PermissionDeniedException(String message){
        super(message);
    }
}
