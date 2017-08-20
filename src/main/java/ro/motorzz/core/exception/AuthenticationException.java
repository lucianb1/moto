package ro.motorzz.core.exception;

/**
 * Created by Luci on 26-May-17.
 */
public class AuthenticationException extends RuntimeException {
    private static final long serialVersionUID = 452677945614331968L;

    public AuthenticationException(String message){
        super(message);
    }
}
