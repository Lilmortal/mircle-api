package nz.co.mircle.v1.api.user.services;

import com.amazonaws.AmazonServiceException;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.model.UserDTO;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

/** Lists of services that can be used to call the user repository. */
public interface UserService {
  void createUser(User user) throws FileNotFoundException, MalformedURLException;

  User findUser(Long id);

  User findUser(String emailAddress);

  User login(UserDTO user) throws Exception;

  User setUserProfileImage(User user, URL profileImage) throws AmazonServiceException;

  void deleteUser(Long id);
}
