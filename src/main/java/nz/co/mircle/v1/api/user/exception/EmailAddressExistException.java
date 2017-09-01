package nz.co.mircle.v1.api.user.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by tanj1 on 25/08/2017.
 */
public class EmailAddressExistException extends AuthenticationException {
    public EmailAddressExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public EmailAddressExistException(String msg) {
        super(msg);
    }
}
