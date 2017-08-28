package nz.co.mircle.v1.api.user.services;

import com.amazonaws.AmazonServiceException;
import nz.co.mircle.v1.api.security.exception.EmailAddressExistException;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.security.model.UserDTO;

import java.net.URL;
import java.util.List;

/** Lists of services that can be used to call the user repository. */
public interface UserService {
  void createUser(User user) throws EmailAddressExistException;

  User findUser(Long id);

  User findUser(String emailAddress);

  User addFriend(Long id);

  List<User> findFriends(Long id);

  User setUserProfileImage(User user, URL profileImage) throws AmazonServiceException;

  void deleteUser(Long id);
}
