package nz.co.mircle.v1.api.iam.services;

import nz.co.mircle.v1.api.iam.exception.EmailAddressExistException;
import nz.co.mircle.v1.api.iam.exception.InvalidAuthenticationException;
import nz.co.mircle.v1.api.iam.model.UserDTO;
import nz.co.mircle.v1.api.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by tanj1 on 25/08/2017.
 */
public interface AuthenticationService {
    User login(UserDTO user) throws InvalidAuthenticationException;

    void createUser(User user) throws EmailAddressExistException;

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
