package nz.co.mircle.v1.api.iam.services;

import nz.co.mircle.v1.api.iam.exception.InvalidAuthenticationException;
import nz.co.mircle.v1.api.iam.model.UserDTO;
import nz.co.mircle.v1.api.user.model.User;

/** Created by tanj1 on 25/08/2017. */
public interface AuthenticationService {
  User login(UserDTO user) throws InvalidAuthenticationException;
}
