package nz.co.mircle.v1.api.iam.controller;

import com.amazonaws.AmazonServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.net.URL;
import nz.co.mircle.v1.api.iam.exception.EmailAddressExistException;
import nz.co.mircle.v1.api.iam.services.AuthenticationService;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** Created by tanj1 on 25/08/2017. */
@RestController
@Api(value = "Registration", description = "Authentication API")
@RequestMapping("/register")
public class AuthenticationController {
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  private AuthenticationService authenticationService;

  private UserService userService;

  private ProfileImageService profileImageService;

  @Autowired
  public AuthenticationController(
      AuthenticationService authenticationService,
      UserService userService,
      ProfileImageService profileImageService) {
    this.authenticationService = authenticationService;
    this.userService = userService;
    this.profileImageService = profileImageService;
  }

  @ApiOperation(value = "Validate if user exist", response = Iterable.class)
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "User exist"),
      @ApiResponse(code = 201, message = "User exist"),
      @ApiResponse(code = 401, message = "You are not authorized to validate a user."),
      @ApiResponse(
        code = 403,
        message = "Accessing the resource you were trying to reach is forbidden"
      ),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
  )
  @GetMapping("/user/validate")
  public ResponseEntity validateUserExist(@RequestParam("emailaddress") String emailAddress) {
    LOG.info(String.format("Validating if %s exist...", emailAddress));
    try {
      User user = userService.findUser(emailAddress);
      if (user != null) {
        LOG.error(String.format("Email address %s already exist.", emailAddress));
        return new ResponseEntity<>(
            String.format("Email address %s already exist.", emailAddress),
            HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } catch (UsernameNotFoundException e) {
      LOG.info(String.format("%s is available.", emailAddress));
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ApiOperation(value = "Register a user", response = Iterable.class)
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Successfully registered a user"),
      @ApiResponse(code = 201, message = "Successfully registered a user"),
      @ApiResponse(code = 401, message = "You are not authorized to register a user."),
      @ApiResponse(
        code = 403,
        message = "Accessing the resource you were trying to reach is forbidden"
      ),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
  )
  @PostMapping
  public ResponseEntity registerUser(@RequestBody User user) {
    LOG.info("Registering a new user...");
    try {
      authenticationService.createUser(user);
      LOG.info(String.format("User ID %d registered.", user.getId()));
    } catch (EmailAddressExistException e) {
      LOG.error(String.format("Email address %s already exist.", user.getEmailAddress()));
      LOG.error(e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
  }

  @ApiOperation(value = "Register the user profile image", response = Iterable.class)
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Successfully registered user profile image"),
      @ApiResponse(code = 201, message = "Successfully registered user profile image"),
      @ApiResponse(
        code = 401,
        message = "You are not authorized to register the user profile image."
      ),
      @ApiResponse(
        code = 403,
        message = "Accessing the resource you were trying to reach is forbidden"
      ),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
  )
  @PostMapping(value = "/{id}/profileimage")
  public ResponseEntity registerUserProfileImage(
      @RequestParam(value = "profileimage", required = false) MultipartFile profileImage,
      @PathVariable(value = "id") Long id) {
    try {
      LOG.info(
          String.format(
              "Retrieving User ID %d from the database to register its profile image...", id));
      User user = userService.findUser(id);

      if (user.getProfileImage() != null) {
        return new ResponseEntity<>(
            "The user already has it's profile image set or it does not exist.",
            HttpStatus.INTERNAL_SERVER_ERROR);
      }

      URL profileImageUrl;
      if (profileImage == null) {
        profileImageUrl = profileImageService.getDefaultImage();
      } else {
        profileImageUrl =
            profileImageService.uploadProfileImageToS3(profileImage, user.getEmailAddress());
      }
      userService.setUserProfileImage(user, profileImageUrl);
      LOG.info(
          String.format(
              "%s %s successfully has its profile image set to %s.",
              user.getFirstName(), user.getSurname(), profileImageUrl));

    } catch (UsernameNotFoundException e) {
      LOG.error("User does not exist.");
      LOG.error(e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (AmazonServiceException e) {
      LOG.error("Failed to register the user profile image; there is an issue with Amazon.");
      LOG.error(e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (IOException e) {
      LOG.error("Failed to register the user profile image.");
      LOG.error(e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
