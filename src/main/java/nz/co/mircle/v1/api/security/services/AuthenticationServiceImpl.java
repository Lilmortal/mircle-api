package nz.co.mircle.v1.api.security.services;

import nz.co.mircle.v1.api.security.exception.EmailAddressNotFoundException;
import nz.co.mircle.v1.api.security.exception.InvalidPasswordException;
import nz.co.mircle.v1.api.security.model.UserDTO;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by tanj1 on 25/08/2017.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @Override
    public User login(UserDTO userDto) throws EmailAddressNotFoundException, InvalidPasswordException {
        User user = userService.findUser(userDto.getEmailAddress());
        if (user == null) {
            throw new EmailAddressNotFoundException(String.format("Email address %s does not exist.", userDto.getEmailAddress()));
        }

        if (!encoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Password does not match");
        }
        return user;
    }
}
