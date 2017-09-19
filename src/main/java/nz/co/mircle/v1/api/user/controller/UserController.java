package nz.co.mircle.v1.api.user.controller;

import com.amazonaws.AmazonServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import nz.co.mircle.v1.api.feeds.model.Feed;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            LOG.info(String.format("User ID %d found.", user.getId()));
        } catch (UsernameNotFoundException e) {
            LOG.error(String.format("Attempt to get the user failed - user ID %d not found.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
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
    @PatchMapping
    public ResponseEntity updateUser(
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "birthDate", required = false) String birthDate,
            @RequestParam(value = "occupation", required = false) String occupation) {
        try {
            LOG.info(String.format("Updating %s details...", emailAddress));
            User user = userService.findUser(emailAddress);

            if (!StringUtils.isBlank(gender)) {
                user.setGender(gender);
            }

            if (!StringUtils.isBlank(phoneNumber)) {
                user.setPhoneNumber(phoneNumber);
            }

            if (!StringUtils.isBlank(birthDate)) {
                // Dont know why dd/MM/yyyy passed from JS does not convert to LocalDateTime here. This is temp for now.
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDateTime dateTime = LocalDate.parse(birthDate, formatter).atStartOfDay();
                user.setBirthDate(dateTime);
            }

            if (!StringUtils.isBlank(occupation)) {
                user.setOccupation(occupation);
            }

            userService.saveUser(user);
            LOG.info(
                    String.format("%s %s successfully updated.", user.getFirstName(), user.getSurname()));
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Update the user password via ID", response = Iterable.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully updated the user password"),
                    @ApiResponse(code = 201, message = "Successfully updated the user password"),
                    @ApiResponse(code = 401, message = "You are not authorized to update the user password."),
                    @ApiResponse(
                            code = 403,
                            message = "Accessing the resource you were trying to reach is forbidden"
                    ),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            }
    )
    @PatchMapping(value = "/password", params = {"id"})
    public ResponseEntity givenIdUpdateUserPassword(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword) {
        try {
            LOG.info(String.format("Getting user ID %d...", id));
            User user = userService.findUser(id);
            updateUserPassword(user, oldPassword, newPassword);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Update the user password", response = Iterable.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully updated the user password"),
                    @ApiResponse(code = 201, message = "Successfully updated the user password"),
                    @ApiResponse(code = 401, message = "You are not authorized to update the user password."),
                    @ApiResponse(
                            code = 403,
                            message = "Accessing the resource you were trying to reach is forbidden"
                    ),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            }
    )
    @PatchMapping(value = "/password", params = {"emailAddress"})
    public ResponseEntity givenEmailAddressUpdateUserPassword(
            @RequestParam(value = "emailAddress") String emailAddress,
            @RequestParam(value = "oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword) {
        try {
            LOG.info(String.format("Getting %s...", emailAddress));
            User user = userService.findUser(emailAddress);
            updateUserPassword(user, oldPassword, newPassword);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
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
    @PostMapping("/profileimage")
    public ResponseEntity givenIdUpdateUserProfileImage(
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam(value = "id") Long id) {
        try {
            LOG.info(String.format("Getting user ID %d...", id));
            User user = userService.findUser(id);
            updateUserProfile(user, profileImage);
        } catch (AmazonServiceException e) {
            LOG.error("Failed to update the user profile image; there is an issue with Amazon.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOG.error("Failed to update the user profile image.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
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
    @PostMapping(value = "/profileimage", params = {"emailAddress"})
    public ResponseEntity givenEmailAddressUpdateUserProfileImage(
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam(value = "emailAddress") String emailAddress) {
        try {
            LOG.info(String.format("Getting %s...", emailAddress));
            User user = userService.findUser(emailAddress);
            updateUserProfile(user, profileImage);
        } catch (AmazonServiceException e) {
            LOG.error("Failed to update the user profile image; there is an issue with Amazon.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOG.error("Failed to update the user profile image.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        LOG.info(String.format("Deleting user with id %s...", id));

        try {
            User user = userService.findUser(id);
            userService.deleteUser(user);
            LOG.info(String.format("User %d deleted.", id));
        } catch (Exception e) {
            LOG.error(String.format("Attempt to delete user with id %d failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
    @DeleteMapping("/email/{emailAddress:.+}")
    public ResponseEntity deleteUserByEmailAddress(
            @PathVariable("emailAddress") String emailAddress) {
        LOG.info(String.format("Deleting %s...", emailAddress));

        try {
            User user = userService.findUser(emailAddress);
            userService.deleteUser(user);
            LOG.info(String.format("%s deleted.", emailAddress));
        } catch (Exception e) {
            LOG.error(String.format("Attempt to delete %s failed.", emailAddress));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
    @PostMapping(value = "/{id}/friend/{friendId}")
    public ResponseEntity givenUserIdAddFriend(
            @PathVariable("id") Long id, @PathVariable Long friendId) {
        LOG.info(String.format("Adding user ID %d friend ID...", id));

        try {
            User user = userService.findUser(id);
            User friend = userService.findUser(friendId);
            userService.addFriend(user, friend);
            LOG.info(String.format("%s %s is now on %s %s friends list.", friend.getFirstName(), friend.getSurname(), user.getFirstName(), user.getSurname()));
        } catch (Exception e) {
            LOG.error(String.format("Attempt to find a user with id %d friends failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
    @PostMapping(value = "/email/{emailAddress}/friend/{friendId}")
    public ResponseEntity givenUserEmailAddressAddFriend(
            @PathVariable("id") String emailAddress, @PathVariable Long friendId) {
        LOG.info(String.format("Adding %s friend ID...", emailAddress));

        try {
            User user = userService.findUser(emailAddress);
            User friend = userService.findUser(friendId);
            userService.addFriend(user, friend);
            LOG.info(String.format("%s %s is now on %s %s friends list.", friend.getFirstName(), friend.getSurname(), user.getFirstName(), user.getSurname()));
        } catch (Exception e) {
            LOG.error(String.format("Attempt to find %s friends failed.", emailAddress));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
    @GetMapping("/{id}/friends")
    public ResponseEntity givenUserIdFindAllFriends(@PathVariable("id") Long id) {
        LOG.info(String.format("Getting user ID %d friends...", id));

        Set<User> friends;
        try {
            friends = userService.findFriends(id);
            LOG.info(String.format("User %d friends found.", id));
        } catch (Exception e) {
            LOG.error(String.format("Attempt to find a user with id %d friends failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(friends, HttpStatus.OK);
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
    @GetMapping("/email/{emailAddress}/friends")
    public ResponseEntity givenUserEmailAddressFindAllFriends(@PathVariable("id") Long id) {
        LOG.info(String.format("Getting user ID %d friends...", id));

        Set<User> friends;
        try {
            friends = userService.findFriends(id);
            LOG.info(String.format("User %d friends found.", id));
        } catch (Exception e) {
            LOG.error(String.format("Attempt to find a user with id %d friends failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(friends, HttpStatus.OK);
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
    @DeleteMapping("/{id}/friend/{friendId}")
    public ResponseEntity deleteFriend(
            @PathVariable("id") Long id, @PathVariable("friendId") Long friendId) {
        LOG.info(String.format("Getting user ID %d friends...", id));

        try {
            userService.deleteFriend(id, friendId);
            LOG.info("User %d friends found.");
        } catch (Exception e) {
            LOG.error(String.format("Attempt to find a user with id %d friends failed.", id));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Add feed", response = Iterable.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully add feed"),
                    @ApiResponse(code = 201, message = "Successfully add feed"),
                    @ApiResponse(code = 401, message = "You are not authorized to add feed."),
                    @ApiResponse(
                            code = 403,
                            message = "Accessing the resource you were trying to reach is forbidden"
                    ),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            }
    )
    @PostMapping("/{id}/feed")
    public ResponseEntity addFeed(@PathVariable("id") Long id, @RequestBody Feed feed) {
        LOG.info("Adding feed...");

        try {
            userService.addFeed(id, feed);
        } catch (AmazonServiceException e) {
            LOG.error("Failed to add feed.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOG.info("Feed successfully added.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Get feed", response = Iterable.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully add feed"),
                    @ApiResponse(code = 201, message = "Successfully add feed"),
                    @ApiResponse(code = 401, message = "You are not authorized to add feed."),
                    @ApiResponse(
                            code = 403,
                            message = "Accessing the resource you were trying to reach is forbidden"
                    ),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            }
    )
    @GetMapping("/{id}/feeds")
    public ResponseEntity getFeeds(@RequestParam Long id) {
        LOG.info(String.format("Getting feed from user ID %d..."), id);

        Set<Feed> feeds;
        try {
            feeds = userService.findFeeds(id);
        } catch (AmazonServiceException e) {
            LOG.error("Failed to get feeds.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOG.info("Feeds successfully retrieved.");
        return new ResponseEntity<>(feeds, HttpStatus.OK);
    }

    private void updateUserPassword(User user, String oldPassword, String newPassword) {
        LOG.info(String.format("Updating %s %s password...", user.getFirstName(), user.getSurname()));

        userService.changePassword(user, oldPassword, newPassword);
        LOG.info(
                String.format(
                        "%s %s password successfully updated.", user.getFirstName(), user.getSurname()));
    }

    private void updateUserProfile(User user, MultipartFile profileImage) throws IOException {
        LOG.info(
                String.format("Setting %s %s profile image...", user.getFirstName(), user.getSurname()));

        URL defaultImage = profileImageService.getDefaultImage();
        if (profileImage != null) {
            if (!user.getProfileImage().getUri().equals(defaultImage)) {
                profileImageService.deleteProfileImage(user.getProfileImage().getUri());
            }
            URL profileImageUrl =
                    profileImageService.uploadProfileImageToS3(profileImage, user.getEmailAddress());
            userService.setUserProfileImage(user, profileImageUrl);
        } else {
            userService.setUserProfileImage(user, defaultImage);
        }
        LOG.info(
                String.format(
                        "%s %s successfully has its profile image set to %s.",
                        user.getFirstName(), user.getSurname(), user.getProfileImage().getUri()));
    }
}
