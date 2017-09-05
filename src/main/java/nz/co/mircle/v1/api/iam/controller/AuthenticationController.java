package nz.co.mircle.v1.api.iam.controller;

import com.amazonaws.AmazonServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import nz.co.mircle.v1.api.iam.exception.InvalidAuthenticationException;
import nz.co.mircle.v1.api.iam.model.UserDTO;
import nz.co.mircle.v1.api.iam.services.AuthenticationService;
import nz.co.mircle.v1.api.iam.exception.EmailAddressExistException;
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

import java.io.IOException;
import java.net.URL;

/**
 * Created by tanj1 on 25/08/2017.
 */
@RestController
@Api(value = "authentication", description = "Authentication API")
public class AuthenticationController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileImageService profileImageService;

//    @ApiOperation(value = "Login", response = Iterable.class)
//    @ApiResponses(
//            value = {
//                    @ApiResponse(code = 200, message = "Successfully login"),
//                    @ApiResponse(code = 201, message = "Successfully login"),
//                    @ApiResponse(code = 401, message = "You are not authorized to login."),
//                    @ApiResponse(
//                            code = 403,
//                            message = "Accessing the resource you were trying to reach is forbidden"
//                    ),
//                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
//            }
//    )
//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody UserDTO userDTO) {
//        LOG.info(
//                String.format("Attempting to login with email address %s...", userDTO.getUsername()));
//
//        User user;
//        try {
//            user = authenticationService.login(userDTO);
//            LOG.info(String.format("User ID %d login.", user.getId()));
//        } catch (InvalidAuthenticationException e) {
//            LOG.error("Attempt to create a new user failed.");
//            LOG.error(e.getMessage());
//            return new ResponseEntity<>(
//                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

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
    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody User user) {
        LOG.info("Creating a new user...");
        try {
            authenticationService.createUser(user);
            LOG.info(String.format("User ID %d created.", user.getId()));
        } catch (EmailAddressExistException e) {
            LOG.error("Attempt to create a new user failed.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(
                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
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
    @PostMapping("/register/email/{emailAddress}/profileimage")
    public ResponseEntity registerUserProfileImage(
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @PathVariable("emailAddress") String emailAddress) {
        LOG.info(String.format("Getting %s...", emailAddress));
        try {
            User user = userService.findUser(emailAddress);
            if (user.getProfileImage() != null) {
                return new ResponseEntity<>(new FailedResponse("The user already has it's profile image set or it does not exist."), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            URL profileImageUrl;
            if (profileImage == null) {
                profileImageUrl = profileImageService.getDefaultImage();
            } else {
                profileImageUrl = profileImageService.uploadProfileImageToS3(profileImage, emailAddress);
            }
            userService.setUserProfileImage(user, profileImageUrl);
            LOG.info(
                    String.format(
                            "%s %s successfully has its profile image set to %s.",
                            user.getFirstName(), user.getSurname(), profileImageUrl));
        } catch (AmazonServiceException e) {
            LOG.error("Failed to get the default profile image");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(
                    new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOG.error("Failed to get the default profile image");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
