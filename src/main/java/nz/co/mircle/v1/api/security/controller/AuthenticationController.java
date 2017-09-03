package nz.co.mircle.v1.api.security.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.security.Principal;
import nz.co.mircle.v1.api.security.exception.InvalidAuthenticationException;
import nz.co.mircle.v1.api.security.model.UserDTO;
import nz.co.mircle.v1.api.security.services.AuthenticationService;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.lib.failedResponse.model.FailedResponse;
import nz.co.mircle.v1.security.JwtGenerator;
import nz.co.mircle.v1.security.model.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Created by tanj1 on 25/08/2017. */
@RestController
@EnableOAuth2Sso
@Api(value = "authentication", description = "Authentication API")
@RequestMapping("/login")
public class AuthenticationController {
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired private AuthenticationService authenticationService;

  @Autowired
  private JwtGenerator jwtGenerator;

  @ApiOperation(value = "Login", response = Iterable.class)
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Successfully login"),
      @ApiResponse(code = 201, message = "Successfully login"),
      @ApiResponse(code = 401, message = "You are not authorized to login."),
      @ApiResponse(
        code = 403,
        message = "Accessing the resource you were trying to reach is forbidden"
      ),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
  )
  @PostMapping
  public ResponseEntity login(@RequestBody UserDTO userDTO) {
    LOG.info(
        String.format("Attempting to login with email address %s...", userDTO.getEmailAddress()));

    User user;
    try {
      user = authenticationService.login(userDTO);
      LOG.info(String.format("User ID %d login.", user.getId()));
    } catch (InvalidAuthenticationException e) {
      LOG.error("Attempt to create a new user failed.");
      LOG.error(e.getMessage());
      return new ResponseEntity<>(
          new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @PostMapping("/token")
  public String generateToken(@RequestBody final JwtUser jwtUser) {
    return jwtGenerator.generate(jwtUser);
  }
}
