package nz.co.mircle.v1.api.user.services;

import java.net.URL;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.security.exception.EmailAddressExistException;
import nz.co.mircle.v1.api.user.dao.UserRepository;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.security.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * List of user services implementation that are used to call the repository.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private ProfileImageService profileImageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void createUser(User user) throws EmailAddressExistException {
        if (findUser(user.getEmailAddress()) != null) {
            throw new EmailAddressExistException(String.format("Email address %s already exist.", user.getEmailAddress()));
        }

        LocalDateTime currentDateTime = LocalDateTime.now(Clock.systemUTC());
        user.setCreatedOn(currentDateTime);
        user.setLastLoggedIn(currentDateTime);
        user.setLoggedIn(false);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    @Override
    public User findUser(Long id) {
        User user = userRepository.findOne(id);
        return user;
    }

    @Override
    public User findUser(String emailAddress) {
        User user = userRepository.findByEmailAddress(emailAddress);
        return user;
    }

    @Override
    public User addFriend(Long id) {
        return null;
    }

    @Override
    public List<User> findFriends(Long id) {
        List<User> friends = userRepository.findByFriendId(id);
        return friends;
    }

    @Override
    public User setUserProfileImage(User user, URL profileImage) throws AmazonServiceException {
        user.getProfileImage().setUri(profileImage);
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailAddress(username);
    }
}
