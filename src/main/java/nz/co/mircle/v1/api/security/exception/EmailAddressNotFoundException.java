package nz.co.mircle.v1.api.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by tanj1 on 25/08/2017.
 */
public class EmailAddressNotFoundException extends AuthenticationException {
    public EmailAddressNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public EmailAddressNotFoundException(String msg) {
        super(msg);
    }
}
