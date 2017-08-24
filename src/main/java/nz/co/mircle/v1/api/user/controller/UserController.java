package nz.co.mircle.v1.api.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDateTime;
import nz.co.mircle.v1.api.AbstractController;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.model.UserDTO;
import nz.co.mircle.v1.api.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Here are a lists of user API. */
@RestController
@Api(value = "user", description = "User API")
@RequestMapping("/user")
public class UserController extends AbstractController {
  private final Logger LOG = LoggerFactory.getLogger(UserController.class);

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @ApiOperation(value = "Create a user", response = Iterable.class)
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Successfully created a user"),
      @ApiResponse(code = 201, message = "Successfully created a user"),
      @ApiResponse(code = 401, message = "You are not authorized to create a user."),
      @ApiResponse(
        code = 403,
        message = "Accessing the resource you were trying to reach is forbidden"
      ),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
  )
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity createUser(@RequestBody User user) {
    LOG.info("Creating a new user...");

    try {
      userService.createUser(user);
      LOG.info(String.format("User ID %d created.", user.getId()));
    } catch (Exception e) {
      LOG.error("Attempt to create a new user failed.");
      LOG.error(e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
  }

  @ApiOperation(value = "Getting a user by id", response = Iterable.class)
  @ApiResponses(
          value = {
                  @ApiResponse(code = 200, message = "Successfully retrieved a user"),
                  @ApiResponse(code = 201, message = "Successfully retrieved a user"),
                  @ApiResponse(code = 401, message = "You are not authorized to retrieved a user."),
                  @ApiResponse(
                          code = 403,
                          message = "Accessing the resource you were trying to reach is forbidden"
                  ),
                  @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
          }
  )
  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
  public ResponseEntity getUserById(@PathVariable("id") Long id) {
    LOG.info(String.format("Getting user ID %d...", id));

    User user;
    try {
      user = userService.findUser(id);
      LOG.info(String.format("User %d found.", user.getId()));
    } catch (Exception e) {
      LOG.error(String.format("Attempt to find a user with id %d failed.", id));
      LOG.error(e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @ApiOperation(value = "Getting a user by id", response = Iterable.class)
  @ApiResponses(
          value = {
                  @ApiResponse(code = 200, message = "Successfully retrieved a user"),
                  @ApiResponse(code = 201, message = "Successfully retrieved a user"),
                  @ApiResponse(code = 401, message = "You are not authorized to retrieved a user."),
                  @ApiResponse(
                          code = 403,
                          message = "Accessing the resource you were trying to reach is forbidden"
                  ),
                  @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
          }
  )
  @RequestMapping(value = "/user", method = RequestMethod.POST)
  public ResponseEntity getUserByEmailAddressAndPassword(@RequestBody UserDTO userDTO) {
    LOG.info(String.format("Determine if %s is a valid user and has the correct password...", userDTO.getEmailAddress()));

    User user;
    try {
      user = userService.login(userDTO);
      LOG.info(String.format("User %d found.", user.getId()));
    } catch (Exception e) {
      LOG.error(String.format("Attempt to find user with email address %s failed.", userDTO.getEmailAddress()));
      LOG.error(e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @ApiOperation(value = "Delete a user", response = Iterable.class)
  @ApiResponses(
          value = {
                  @ApiResponse(code = 200, message = "Successfully deleted a user"),
                  @ApiResponse(code = 201, message = "Successfully deleted a user"),
                  @ApiResponse(code = 401, message = "You are not authorized to retrieved a user."),
                  @ApiResponse(
                          code = 403,
                          message = "Accessing the resource you were trying to reach is forbidden"
                  ),
                  @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
          }
  )
  @RequestMapping(value = "/user", method = RequestMethod.DELETE)
  public ResponseEntity deleteUser(@PathVariable("id") Long id) {
    LOG.info(String.format("Deleting user with id %s...", id));

    try {
      userService.deleteUser(id);
      LOG.info(String.format("User %d deleted.", id));
    } catch (Exception e) {
      LOG.error(String.format("Attempt to delete user with id %d failed.", id));
      LOG.error(e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
