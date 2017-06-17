package nz.co.mircle.user.services;

import nz.co.mircle.user.model.User;

/**
 * Created by Jack on 01/06/2017.
 */
public interface UserService {
    void createUser(User user);

    User findUser(Long id);
}
