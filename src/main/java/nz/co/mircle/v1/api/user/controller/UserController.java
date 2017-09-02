package nz.co.mircle.v1.api.user.controller;

import com.amazonaws.AmazonServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nz.co.mircle.v1.api.AbstractController;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.user.exception.EmailAddressExistException;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import nz.co.mircle.v1.lib.failedResponse.model.FailedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.util.List;

/**
 * Here are a lists of user API.
 */
@RestController
@EnableResourceServer
@Api(value = "user", description = "User API")
@RequestMapping("/user")
public class UserController extends AbstractController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private UserService userService;

    private ProfileImageService profileImageService;

    @Autowired
    public UserController(UserService userService, ProfileImageService profileImageService) {
        this.userService = userService;
        this.profileImageService = profileImageService;
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
        } catch (EmailAddressExistException e) {
            LOG.error("Attempt to create a new user failed.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
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
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserById(@PathVariable("id") Long id) {
        LOG.info(String.format("Getting user ID %d...", id));

        User user;
        try {
            user = userService.findUser(id);
            LOG.info(String.format("User %d found.", user.getId()));
        } catch (Exception e) {
            LOG.error(String.format("Attempt to find a user with id %d failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Set the user profile image to be default", response = Iterable.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully set the user profile image to be default"),
                    @ApiResponse(code = 201, message = "Successfully set the user profile image to be default"),
                    @ApiResponse(
                            code = 401,
                            message = "You are not authorized to get the profile image from AWS S3."
                    ),
                    @ApiResponse(
                            code = 403,
                            message = "Accessing the resource you were trying to reach is forbidden"
                    ),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            }
    )
    @RequestMapping(value = "/profileimage", method = RequestMethod.POST)
    public ResponseEntity setUserProfileImage(@RequestParam(value = "profileImage", required = false) MultipartFile profileImage, @RequestParam("emailAddress") String emailAddress) {
        LOG.info(String.format("Getting %s...", emailAddress));
        try {
            User user = userService.findUser(emailAddress);
            URL profileImageUrl;
            if (profileImage == null) {
                profileImageUrl = profileImageService.getDefaultImage();
            } else {
                profileImageUrl = profileImageService.uploadProfileImageToS3(profileImage, emailAddress);
            }
            userService.setUserProfileImage(user, profileImageUrl);
            LOG.info(String.format("%s %s successfully has its profile image set to %s.", user.getFirstName(), user.getSurname(), profileImageUrl));
        } catch (AmazonServiceException e) {
            LOG.error("Failed to get the default profile image");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOG.error("Failed to get the default profile image");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
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
    @RequestMapping(value = "/{id}/profileimage", method = RequestMethod.DELETE)
    public ResponseEntity removeUserProfileImage(@PathVariable("id") Long id) {
        LOG.info(String.format("Getting user ID %d...", id));

        try {
            User user = userService.findUser(id);
            // Remove that folder from AWS
            URL defaultImage = profileImageService.getDefaultImage();
            userService.setUserProfileImage(user, defaultImage);
            LOG.info(String.format("%s %s profile image successfully removed.", user.getFirstName(), user.getSurname()));
        } catch (Exception e) {
            LOG.error(String.format("Attempt to find a user with id %d failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
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
    @RequestMapping(value = "/{id}/friend/{friendId}", method = RequestMethod.POST)
    public ResponseEntity addFriend(@PathVariable("id") Long id, @PathVariable Long friendId, Principal principal) {
        LOG.info(String.format("Adding user ID %d friend ID...", id));

        User currentUser = userService.findUser(principal.getName());
        try {
            if (currentUser.getId() == id) {
                userService.addFriend(id, friendId);
                LOG.info("User %d friends found.");
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            LOG.error(String.format("Attempt to find a user with id %d friends failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
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
    @RequestMapping(value = "/{id}/friends", method = RequestMethod.GET)
    public ResponseEntity findFriends(@PathVariable("id") Long id) {
        LOG.info(String.format("Getting user ID %d friends...", id));

        List<User> friends;
        try {
            friends = userService.findFriends(id);
            LOG.info("User %d friends found.");
        } catch (Exception e) {
            LOG.error(String.format("Attempt to find a user with id %d friends failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(friends, HttpStatus.CREATED);
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
    @RequestMapping(value = "/{id}/friend/{friendId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteFriend(@PathVariable("id") Long id, @PathVariable("friendId") Long friendId) {
        LOG.info(String.format("Getting user ID %d friends...", id));

        try {
            userService.deleteFriend(id, friendId);
            LOG.info("User %d friends found.");
        } catch (Exception e) {
            LOG.error(String.format("Attempt to find a user with id %d friends failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
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
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") Long id, Principal principal) {
        LOG.info(String.format("Deleting user with id %s...", id));

        User currentUser = userService.findUser(principal.getName());
        try {
            if (currentUser.getId() == id) {
                userService.deleteUser(id);
                LOG.info(String.format("User %d deleted.", id));
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            LOG.error(String.format("Attempt to delete user with id %d failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
