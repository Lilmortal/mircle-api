package nz.co.mircle.v1.api.profileImage.controller;

import com.amazonaws.AmazonServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;

import nz.co.mircle.v1.api.AbstractController;
import nz.co.mircle.v1.api.profileImage.model.ProfileImage;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.user.controller.UserController;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Here are a lists of profile image API.
 */
@RestController
@Api(value = "profile image", description = "Profile image API")
@RequestMapping("/profileimage")
public class ProfileImageController extends AbstractController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileImageService profileImageService;

    @ApiOperation(value = "Get the default profile image", response = Iterable.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully return the default profile image"),
                    @ApiResponse(code = 201, message = "Successfully return the default profile image"),
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
    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public ResponseEntity getDefaultImage() {
        LOG.info("Getting default profile image...");
        URL defaultImage;

        try {
            defaultImage = profileImageService.getDefaultImage();
        } catch (AmazonServiceException e) {
            LOG.error("Failed to get the default profile image");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOG.info("Default profile image successfully retrieved.");
        return new ResponseEntity<>(defaultImage, HttpStatus.OK);
    }

    @ApiOperation(value = "Upload a profile image to S3", response = Iterable.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully upload a profile image to S3"),
                    @ApiResponse(code = 201, message = "Successfully upload a profile image to S3"),
                    @ApiResponse(
                            code = 401,
                            message = "You are not authorized to upload a profile image to AWS S3."
                    ),
                    @ApiResponse(
                            code = 403,
                            message = "Accessing the resource you were trying to reach is forbidden"
                    ),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            }
    )
    @RequestMapping(value = "/s3upload", method = RequestMethod.POST)
    public ResponseEntity uploadProfileImageToS3(@RequestParam("profileImage") MultipartFile profileImage, @RequestParam("emailAddress") String emailAddress) {
        LOG.info("Uploading profile image to AWS S3...");

        try {
            profileImageService.uploadProfileImageToS3(profileImage, emailAddress);
            LOG.info(String.format("Profile image successfully uploaded."));
        } catch (AmazonServiceException e) {
            LOG.error("Failed to upload the profile image");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FileNotFoundException e) {
            LOG.error("Profile image not found.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (MalformedURLException e) {
            LOG.error("Malformed URL.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOG.error("IO Exception.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
