package nz.co.mircle.user.services;

import nz.co.mircle.user.model.User;

/** Lists of services that can be used to call the user repository. */
public interface UserService {
  void createUser(User user);

  User findUser(Long id);

  User findUser(String emailAddress);
}
