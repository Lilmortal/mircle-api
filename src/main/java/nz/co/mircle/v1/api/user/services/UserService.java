package nz.co.mircle.v1.api.user.services;

import com.amazonaws.AmazonServiceException;
import java.net.URL;
import java.util.List;
import nz.co.mircle.v1.api.user.exception.EmailAddressExistException;
import nz.co.mircle.v1.api.user.model.User;

/** Lists of services that can be used to call the user repository. */
public interface UserService {
  void createUser(User user) throws EmailAddressExistException;

  User findUser(Long id);

  User findUser(String emailAddress);

  void addFriend(Long id, Long friendId);

  List<User> findFriends(Long id);

  void deleteFriend(Long id, Long friendId);

  void setUserProfileImage(User user, URL profileImage) throws AmazonServiceException;

  void deleteUser(Long id);
}
