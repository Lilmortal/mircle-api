package nz.co.mircle.v1.api.userSocialMedia.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nz.co.mircle.v1.api.AbstractController;
import nz.co.mircle.v1.api.socialMedia.model.SocialMedia;
import nz.co.mircle.v1.api.socialMedia.services.SocialMediaService;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import nz.co.mircle.v1.api.userSocialMedia.model.UserSocialMedia;
import nz.co.mircle.v1.api.userSocialMedia.services.UserSocialMediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Here are a lists of user social media API. */
@RestController
@Api(value = "User social media", description = "User social media API")
@RequestMapping("/social/media/user")
public class UserSocialMediaController extends AbstractController {
  private Logger LOG = LoggerFactory.getLogger(UserSocialMediaController.class);

  private UserService userService;

  private SocialMediaService socialMediaService;

  private UserSocialMediaService userSocialMediaService;

  @Autowired
  public UserSocialMediaController(
      UserService userService,
      SocialMediaService socialMediaService,
      UserSocialMediaService userSocialMediaService) {
    this.userService = userService;
    this.socialMediaService = socialMediaService;
    this.userSocialMediaService = userSocialMediaService;
  }

  @ApiOperation(value = "Create a user social media", response = Iterable.class)
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Successfully created a user social media"),
      @ApiResponse(code = 201, message = "Successfully created a user social media"),
      @ApiResponse(code = 401, message = "You are not authorized to create a user social media."),
      @ApiResponse(
        code = 403,
        message = "Accessing the resource you were trying to reach is forbidden"
      ),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
  )
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ResponseEntity createUserSocialMedia(
      @RequestParam("userId") Long userId,
      @RequestParam("socialMediaId") Long socialMediaId,
      @RequestParam("url") String url) {
    LOG.info("Creating a new user social media...");

    try {
      User user = userService.findUser(userId);
      SocialMedia socialMedia = socialMediaService.findSocialMedia(socialMediaId);
      UserSocialMedia userSocialMedia = new UserSocialMedia(user, socialMedia, url);
      userSocialMediaService.createUserSocialMedia(userSocialMedia);

      LOG.info("User social media created.");
    } catch (Exception e) {
      LOG.error("Attempt to create a new user social media failed.");
      LOG.error(e.getMessage());
      return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity(HttpStatus.CREATED);
  }
}
