package nz.co.mircle.userSocialMedia.controller;

import nz.co.mircle.AbstractController;
import nz.co.mircle.userSocialMedia.services.UserSocialMediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jacktan on 17/06/17.
 */
@RestController
@RequestMapping("/social/media/user")
public class UserSocialMediaController extends AbstractController{
    private Logger LOG = LoggerFactory.getLogger(UserSocialMediaController.class);

    private UserSocialMediaService userSocialMediaService;

    @Autowired
    public UserSocialMediaController(UserSocialMediaService userSocialMediaService) {
        this.userSocialMediaService = userSocialMediaService;
    }

    @RequestMapping(name = "/create", method = RequestMethod.POST)
    public ResponseEntity createUserSocialMedia(@RequestParam("userId") String userId, @RequestParam("socialMediaId") String socialMediaId, @RequestParam("url") String url) {

        //userSocialMediaService.createUserSocialMedia();
        return null;
    }

}
