package nz.co.mircle.v1.api.user.services;

import com.amazonaws.AmazonServiceException;

import java.net.URL;
import java.util.List;

import nz.co.mircle.v1.api.profileImage.model.ProfileImage;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.user.dao.UserRepository;
import nz.co.mircle.v1.api.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * List of user services implementation that are used to call the repository.
 */
@Service
public class UserServiceImpl implements UserService {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private ProfileImageService profileImageService;

    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(
            ProfileImageService profileImageService,
            UserRepository userRepository,
            BCryptPasswordEncoder encoder) {
        this.profileImageService = profileImageService;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User findUser(Long id) throws UsernameNotFoundException {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with ID %d not found.", id));
        }
        return user;
    }

    @Override
    public User findUser(String emailAddress) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAddress(emailAddress);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s not found.", emailAddress));
        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void setUserProfileImage(User user, URL profileImageUrl) throws AmazonServiceException {
        ProfileImage profileImage = user.getProfileImage();
        if (profileImage == null) {
            profileImage = new ProfileImage();
        }
        profileImage.setUri(profileImageUrl);

        user.setProfileImage(profileImage);
        userRepository.save(user);
    }

    @Override
    public void changePassword(User user, String oldPassword, String newPassword) {
        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("New password is not the same as the old password.");
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        User user = userRepository.findById(id);
        User friend = userRepository.findById(friendId);
        //user.getFriends().add(friend);
        userRepository.save(user);
    }

    @Override
    public List<User> findFriends(Long id) {
        User user = userRepository.findById(id);
        //return user.getFriends();
        return null;
    }

    @Override
    public void deleteFriend(Long id, Long friendId) {
        User user = userRepository.findById(id);
        User friend = userRepository.findById(friendId);
        //user.getFriends().remove(friend);
        userRepository.save(user);
    }
}
