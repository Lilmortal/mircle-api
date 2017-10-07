package nz.co.mircle.v1.api.user.services;

import com.amazonaws.AmazonServiceException;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import nz.co.mircle.v1.api.feeds.model.Feed;
import nz.co.mircle.v1.api.profileImage.model.ProfileImage;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.user.dao.UserRepository;
import nz.co.mircle.v1.api.user.model.Friend;
import nz.co.mircle.v1.api.user.model.UserFriend;
import nz.co.mircle.v1.api.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User setUserProfileImage(User user, URL profileImageUrl) throws AmazonServiceException {
        ProfileImage profileImage = user.getProfileImage();
        if (profileImage == null) {
            profileImage = new ProfileImage();
        }
        profileImage.setUri(profileImageUrl);

        user.setProfileImage(profileImage);
        return userRepository.save(user);
    }

    @Override
    public User changePassword(User user, String oldPassword, String newPassword) {
        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("New password is not the same as the old password.");
        }
        user.setPassword(encoder.encode(newPassword));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public Friend addFriend(User user, User friend) {
        LocalDateTime addedTime = LocalDateTime.now();
        UserFriend userUserFriend = new UserFriend(user, friend, addedTime);
        user.getUserFriends().add(userUserFriend);
        userRepository.save(user);

        Friend newFriend = new Friend(user, addedTime);
        return newFriend;
    }

    @Override
    public Set<Friend> findFriends(Long id) {
        User user = userRepository.findById(id);
        Set<Friend> friends = user.getUserFriends().stream().map(userFriend -> {
            Friend friend = new Friend(userFriend.getPk().getFriend(), userFriend.getAddedTime());
            return friend;
        }).collect(Collectors.toSet());
        return friends;
    }

    @Override
    public void deleteFriend(Long id, Long friendId) {
        User user = userRepository.findById(id);
        User friend = userRepository.findById(friendId);
        user.getUserFriends().remove(friend);
        userRepository.save(user);
    }

    @Override
    public void addFeed(Long id, Feed feed) {
        User user = userRepository.findById(id);
        user.getFeeds().add(feed);
        userRepository.save(user);
    }

    @Override
    public Set<Feed> findFeeds(Long id) {
        User user = userRepository.findById(id);
        return user.getFeeds();
    }
}
