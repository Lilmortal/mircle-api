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
import org.springframework.stereotype.Service;

/** List of user services implementation that are used to call the repository. */
@Service
public class UserServiceImpl implements UserService {
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired private ProfileImageService profileImageService;

  @Autowired private UserRepository userRepository;

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
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public void setUserProfileImage(User user, URL profileImageUrl) throws AmazonServiceException {
    if (user.getProfileImage() == null) {
      ProfileImage newProfileImage = new ProfileImage();
      user.setProfileImage(newProfileImage);
    }

    user.getProfileImage().setUri(profileImageUrl);
    userRepository.save(user);
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
