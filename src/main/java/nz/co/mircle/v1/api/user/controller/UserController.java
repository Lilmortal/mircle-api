package nz.co.mircle.v1.api.user.controller;

import com.amazonaws.AmazonServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.util.List;

import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import nz.co.mircle.v1.lib.failedResponse.model.FailedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Here are a lists of user API.
 */
@RestController
@Api(value = "user", description = "User API")
@RequestMapping("/user")
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private UserService userService;

    private ProfileImageService profileImageService;

    @Autowired
    public UserController(UserService userService, ProfileImageService profileImageService) {
        this.userService = userService;
        this.profileImageService = profileImageService;
    }

    @ApiOperation(value = "Retrieving a user by id", response = Iterable.class)
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
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Long id) {
        LOG.info(String.format("Getting user ID %d...", id));

        User user;
        try {
            user = userService.findUser(id);
            LOG.info(String.format("User %d found.", user.getId()));
        } catch (Exception e) {
            LOG.error(String.format("Attempt to find a user with id %d failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(
                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation(value = "Getting a user by email address", response = Iterable.class)
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
    @GetMapping("/email/{emailAddress:.+}")
    public ResponseEntity getUserByEmailAddress(@PathVariable("emailAddress") String emailAddress) {
        LOG.info(String.format("Getting %s details...", emailAddress));

        User user;
        try {
            user = userService.findUser(emailAddress);
            LOG.info(String.format("%s found.", emailAddress));
        } catch (Exception e) {
            LOG.error(String.format("Attempt to find %s failed.", emailAddress));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(
                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
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
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        LOG.info(String.format("Deleting user with id %s...", id));

        try {
            userService.deleteUser(id);
            LOG.info(String.format("User %d deleted.", id));
        } catch (Exception e) {
            LOG.error(String.format("Attempt to delete user with id %d failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(
                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update the user profile image", response = Iterable.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully updated the user profile image"),
                    @ApiResponse(code = 201, message = "Successfully updated the user profile image"),
                    @ApiResponse(
                            code = 401,
                            message = "You are not authorized to update the user profile image."
                    ),
                    @ApiResponse(
                            code = 403,
                            message = "Accessing the resource you were trying to reach is forbidden"
                    ),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            }
    )
    // Query param, search via ID or email address
    @PatchMapping("/profileimage")
    public ResponseEntity setUserProfileImage(
            @RequestParam(value = "profileImage") MultipartFile profileImage,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "emailAddress", required = false) String emailAddress) {
        try {
            User user;
            if (id != null) {
                LOG.info(String.format("Getting user ID %d...", id));
                user = userService.findUser(emailAddress);
            } else if (emailAddress != null) {
                LOG.info(String.format("Getting %s...", emailAddress));
                user = userService.findUser(emailAddress);
            } else {
                return new ResponseEntity<>("Missing user ID or email address", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            URL profileImageUrl;
            if (profileImage == null) {
                LOG.info(String.format("Setting %s %s profile image to be the default profile image...", user.getFirstName(), user.getSurname()));
                profileImageUrl = profileImageService.getDefaultImage();
            } else {
                LOG.info(String.format("Setting %s %s profile image...", user.getFirstName(), user.getSurname()));
                profileImageUrl = profileImageService.uploadProfileImageToS3(profileImage, emailAddress);
            }

            userService.setUserProfileImage(user, profileImageUrl);
            LOG.info(
                    String.format(
                            "%s %s successfully has its profile image set to %s.",
                            user.getFirstName(), user.getSurname(), profileImageUrl));
        } catch (AmazonServiceException e) {
            LOG.error("Failed to update the user profile image; there is an issue with Amazon.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(
                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOG.error("Failed to update the user profile image.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(
                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Remove user profile image", response = Iterable.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully removed user profile image"),
                    @ApiResponse(code = 201, message = "Successfully removed user profile image"),
                    @ApiResponse(code = 401, message = "You are not authorized to removed the user profile image."),
                    @ApiResponse(
                            code = 403,
                            message = "Accessing the resource you were trying to reach is forbidden"
                    ),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            }
    )
    @DeleteMapping("/profileimage")
    public ResponseEntity removeUserProfileImage(@RequestParam(value = "id", required = false) Long id,
                                                 @RequestParam(value = "emailAddress", required = false) String emailAddress) {

        try {
            User user;
            if (id != null) {
                LOG.info(String.format("Getting user ID %d...", id));
                user = userService.findUser(id);
            } else if (emailAddress != null) {
                LOG.info(String.format("Getting %s...", emailAddress));
                user = userService.findUser(emailAddress);
            } else {
                return new ResponseEntity<>("Missing user ID or email address", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            profileImageService.deleteProfileImage(user.getProfileImage().getUri());

            URL defaultImage = profileImageService.getDefaultImage();
            userService.setUserProfileImage(user, defaultImage);
            LOG.info(
                    String.format(
                            "%s %s profile image successfully removed.", user.getFirstName(), user.getSurname()));
        } catch (Exception e) {
            LOG.error(String.format("Attempt to find a user with id %d failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(
                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @ApiOperation(value = "Getting a user by id", response = Iterable.class)
//    @ApiResponses(
//            value = {
//                    @ApiResponse(code = 200, message = "Successfully retrieved a user"),
//                    @ApiResponse(code = 201, message = "Successfully retrieved a user"),
//                    @ApiResponse(code = 401, message = "You are not authorized to retrieved a user."),
//                    @ApiResponse(
//                            code = 403,
//                            message = "Accessing the resource you were trying to reach is forbidden"
//                    ),
//                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
//            }
//    )
//    @PostMapping("/{id}/friend/{friendId}")
//    public ResponseEntity addFriend(
//            @PathVariable("id") Long id, @PathVariable Long friendId) {
//        LOG.info(String.format("Adding user ID %d friend ID...", id));
//
//        try {
//            userService.addFriend(id, friendId);
//            //LOG.info(String.format("%s %s is now on %s %s friends list."), id);
//        } catch (Exception e) {
//            LOG.error(String.format("Attempt to find a user with id %d friends failed.", id));
//            LOG.error(e.getMessage());
//            return new ResponseEntity<>(
//                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "Getting a user by id", response = Iterable.class)
//    @ApiResponses(
//            value = {
//                    @ApiResponse(code = 200, message = "Successfully retrieved a user"),
//                    @ApiResponse(code = 201, message = "Successfully retrieved a user"),
//                    @ApiResponse(code = 401, message = "You are not authorized to retrieved a user."),
//                    @ApiResponse(
//                            code = 403,
//                            message = "Accessing the resource you were trying to reach is forbidden"
//                    ),
//                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
//            }
//    )
//    @GetMapping("/{id}/friends")
//    public ResponseEntity findFriends(@PathVariable("id") Long id) {
//        LOG.info(String.format("Getting user ID %d friends...", id));
//
//        List<User> friends;
//        try {
//            friends = userService.findFriends(id);
//            LOG.info("User %d friends found.");
//        } catch (Exception e) {
//            LOG.error(String.format("Attempt to find a user with id %d friends failed.", id));
//            LOG.error(e.getMessage());
//            return new ResponseEntity<>(
//                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        return new ResponseEntity<>(friends, HttpStatus.CREATED);
//    }
//
//    @ApiOperation(value = "Getting a user by id", response = Iterable.class)
//    @ApiResponses(
//            value = {
//                    @ApiResponse(code = 200, message = "Successfully retrieved a user"),
//                    @ApiResponse(code = 201, message = "Successfully retrieved a user"),
//                    @ApiResponse(code = 401, message = "You are not authorized to retrieved a user."),
//                    @ApiResponse(
//                            code = 403,
//                            message = "Accessing the resource you were trying to reach is forbidden"
//                    ),
//                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
//            }
//    )
//    @DeleteMapping("/{id}/friend/{friendId}")
//    public ResponseEntity deleteFriend(
//            @PathVariable("id") Long id, @PathVariable("friendId") Long friendId) {
//        LOG.info(String.format("Getting user ID %d friends...", id));
//
//        try {
//            userService.deleteFriend(id, friendId);
//            LOG.info("User %d friends found.");
//        } catch (Exception e) {
//            LOG.error(String.format("Attempt to find a user with id %d friends failed.", id));
//            LOG.error(e.getMessage());
//            return new ResponseEntity<>(
//                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
