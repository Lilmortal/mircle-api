package nz.co.mircle.socialMedia.controller;

import nz.co.mircle.AbstractController;
import nz.co.mircle.socialMedia.model.SocialMedia;
import nz.co.mircle.socialMedia.services.SocialMediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by jacktan on 17/06/17.
 */
@RestController
@RequestMapping("/social/media")
public class SocialMediaController extends AbstractController {
    private final Logger LOG = LoggerFactory.getLogger(SocialMediaController.class);

    private SocialMediaService socialMediaService;

    @Autowired
    public SocialMediaController(SocialMediaService socialMediaService) {
        this.socialMediaService = socialMediaService;
    }

    @RequestMapping(name = "/create", method = RequestMethod.POST)
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
