package nz.co.mircle.v1.api.security.services;

import nz.co.mircle.v1.api.security.exception.EmailAddressNotFoundException;
import nz.co.mircle.v1.api.security.exception.InvalidPasswordException;
import nz.co.mircle.v1.api.security.model.UserDTO;
import nz.co.mircle.v1.api.user.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by tanj1 on 25/08/2017.
 */
public interface AuthenticationService {
    User login(UserDTO user) throws EmailAddressNotFoundException, InvalidPasswordException;
}
