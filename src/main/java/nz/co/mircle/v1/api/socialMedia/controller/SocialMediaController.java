package nz.co.mircle.v1.api.socialMedia.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nz.co.mircle.v1.api.AbstractController;
import nz.co.mircle.v1.api.socialMedia.model.SocialMedia;
import nz.co.mircle.v1.api.socialMedia.services.SocialMediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/** Here are a lists of social media API. */
@RestController
@Api(value = "social media", description = "Social Media API")
@RequestMapping("/social/media")
public class SocialMediaController extends AbstractController {
  private final Logger LOG = LoggerFactory.getLogger(SocialMediaController.class);

  private SocialMediaService socialMediaService;

  @Autowired
  public SocialMediaController(SocialMediaService socialMediaService) {
    this.socialMediaService = socialMediaService;
  }

  @ApiOperation(value = "Create a social media", response = Iterable.class)
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Successfully created a social media"),
      @ApiResponse(code = 201, message = "Successfully created a social media"),
      @ApiResponse(code = 401, message = "You are not authorized to create a social media."),
      @ApiResponse(
        code = 403,
        message = "Accessing the resource you were trying to reach is forbidden"
      ),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
  )
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ResponseEntity createSocialMedia(@RequestBody SocialMedia socialMedia) {
    LOG.info("Create a social media...");

    try {
      socialMediaService.createSocialMedia(socialMedia);
      LOG.info("Social media created.");
    } catch (Exception e) {
      LOG.error("Attempt to create a new social media failed.");
      LOG.error(e.getMessage());
      return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity(socialMedia.getId(), HttpStatus.CREATED);
  }
}
