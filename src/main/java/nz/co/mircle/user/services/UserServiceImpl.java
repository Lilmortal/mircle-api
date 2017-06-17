package nz.co.mircle.user.services;

import nz.co.mircle.user.dao.UserRepository;
import nz.co.mircle.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jack on 01/06/2017.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUser(Long id) {
        return userRepository.findById(id);
    }
}
