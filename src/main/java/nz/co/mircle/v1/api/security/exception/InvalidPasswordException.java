package nz.co.mircle.v1.api.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by tanj1 on 25/08/2017.
 */
public class InvalidPasswordException extends AuthenticationException {

    public InvalidPasswordException(String msg) {
        super(msg);
    }

    public InvalidPasswordException(String msg, Throwable t) {
        super(msg, t);
    }
}
