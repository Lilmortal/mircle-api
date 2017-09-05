package nz.co.mircle.v1.api.iam.controller;

import com.amazonaws.AmazonServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import nz.co.mircle.v1.api.iam.services.AuthenticationService;
import nz.co.mircle.v1.api.iam.exception.EmailAddressExistException;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import nz.co.mircle.v1.lib.failedResponse.model.FailedResponse;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

/**
 * Created by tanj1 on 25/08/2017.
 */
@RestController
@Api(value = "authentication", description = "Authentication API")
public class AuthenticationController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private AuthenticationService authenticationService;

    private UserService userService;

    private ProfileImageService profileImageService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserService userService, ProfileImageService profileImageService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.profileImageService = profileImageService;
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
    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody User user) {
        LOG.info("Registering a new user...");
        try {
            authenticationService.createUser(user);
            LOG.info(String.format("User ID %d registered.", user.getId()));
        } catch (EmailAddressExistException e) {
            LOG.error(String.format("Email address %s already exist.", user.getEmailAddress()));
            LOG.error(e.getMessage());
            return new ResponseEntity<>(
                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
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
    @PatchMapping("/register/profileimage")
    public ResponseEntity registerUserProfileImage(
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "emailAddress", required = false) String emailAddress
    ) {
        try {
            User user;
            if (id != null) {
                LOG.info(String.format("Retrieving User ID $d from the database to register its profile image...", id));
                user = userService.findUser(id);
            } else if (emailAddress != null) {
                LOG.info(String.format("Retrieving %s from the database to register its profile image...", emailAddress));
                user = userService.findUser(emailAddress);
            } else {
                return new ResponseEntity<>("Missing User ID or email address.", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (user.getProfileImage() != null) {
                return new ResponseEntity<>(new FailedResponse("The user already has it's profile image set or it does not exist."), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            URL profileImageUrl;
            if (profileImage == null) {
                profileImageUrl = profileImageService.getDefaultImage();
            } else {
                profileImageUrl = profileImageService.uploadProfileImageToS3(profileImage, user.getEmailAddress());
            }
            userService.setUserProfileImage(user, profileImageUrl);
            LOG.info(
                    String.format(
                            "%s %s successfully has its profile image set to %s.",
                            user.getFirstName(), user.getSurname(), profileImageUrl));
        } catch (AmazonServiceException e) {
            LOG.error("Failed to register the user profile image; there is an issue with Amazon.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(
                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOG.error("Failed to register the user profile image.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
