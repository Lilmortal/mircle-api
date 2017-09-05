package nz.co.mircle.v1.api.iam.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidAuthenticationException extends AuthenticationException {
  public InvalidAuthenticationException(String msg, Throwable t) {
    super(msg, t);
  }

  public InvalidAuthenticationException(String msg) {
    super(msg);
  }
}
