package nz.co.mircle.v1.api.user.services;

import nz.co.mircle.v1.api.user.model.User;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

/** Lists of services that can be used to call the user repository. */
public interface UserService {
  void createUser(User user) throws FileNotFoundException, MalformedURLException;

  User findUser(Long id);

  User findUser(String emailAddress);
}
