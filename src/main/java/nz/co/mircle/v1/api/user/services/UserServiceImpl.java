package nz.co.mircle.v1.api.user.services;

import nz.co.mircle.v1.api.user.dao.UserRepository;
import nz.co.mircle.v1.api.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * List of user services implementation that are used to call the repository.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findUser(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress);
    }
}
