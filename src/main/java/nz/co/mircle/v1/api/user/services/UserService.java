package nz.co.mircle.v1.api.user.services;

import nz.co.mircle.v1.api.user.model.User;

/** Lists of services that can be used to call the user repository. */
public interface UserService {
  void createUser(User user);

  User findUser(Long id);

  User findUser(String emailAddress);
}
