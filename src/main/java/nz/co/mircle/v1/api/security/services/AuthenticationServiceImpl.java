package nz.co.mircle.v1.api.security.services;

import nz.co.mircle.v1.api.security.exception.InvalidAuthenticationException;
import nz.co.mircle.v1.api.security.model.UserDTO;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/** Created by tanj1 on 25/08/2017. */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired private BCryptPasswordEncoder encoder;

  @Autowired private UserService userService;

  @Override
  public User login(UserDTO userDto) throws InvalidAuthenticationException {
    User user = userService.findUser(userDto.getEmailAddress());
    if (user == null || !encoder.matches(userDto.getPassword(), user.getPassword())) {
      throw new InvalidAuthenticationException("Invalid username or password.");
    }
    return user;
  }
}
