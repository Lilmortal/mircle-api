package nz.co.mircle.v1.api.iam.services;

import static java.util.Collections.emptyList;

import java.time.Clock;
import java.time.LocalDateTime;

import nz.co.mircle.v1.api.iam.exception.EmailAddressExistException;
import nz.co.mircle.v1.api.user.dao.UserRepository;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by tanj1 on 25/08/2017.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private BCryptPasswordEncoder encoder;

    private UserService userService;

    private UserRepository userRepository;

    @Autowired
    public AuthenticationServiceImpl(
            BCryptPasswordEncoder encoder, UserService userService, UserRepository userRepository) {
        this.encoder = encoder;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) throws EmailAddressExistException {
        try {
            if (userService.findUser(user.getEmailAddress()) != null) {
                throw new EmailAddressExistException(
                        String.format("Email address %s already exist.", user.getEmailAddress()));
            }
        } catch (UsernameNotFoundException e) {
            // This is needed because UsernamePasswordTokenAuthentication only accepts the keyword "username"
            user.setUsername(user.getEmailAddress());
            LocalDateTime currentDateTime = LocalDateTime.now(Clock.systemUTC());
            user.setCreatedOn(currentDateTime);
            user.setLastLoggedIn(currentDateTime);
            user.setLoggedIn(false);
            String hashedPassword = encoder.encode(user.getPassword());
            user.setPassword(hashedPassword);

            userRepository.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), emptyList());
    }
}
