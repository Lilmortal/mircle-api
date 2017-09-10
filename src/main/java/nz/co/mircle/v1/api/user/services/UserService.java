package nz.co.mircle.v1.api.user.services;

import com.amazonaws.AmazonServiceException;
import java.net.URL;
import java.util.List;
import nz.co.mircle.v1.api.user.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/** Lists of services that can be used to call the user repository. */
public interface UserService {
  User findUser(Long id) throws UsernameNotFoundException;

  User findUser(String emailAddress) throws UsernameNotFoundException;

  void saveUser(User user);

  void setUserProfileImage(User user, URL profileImage);

  void changePassword(User user, String oldPassword, String newPassword);

  void deleteUser(User user);

  void addFriend(Long id, Long friendId);

  List<User> findFriends(Long id);

  void deleteFriend(Long id, Long friendId);
}
