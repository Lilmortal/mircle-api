package nz.co.mircle.profile.controller;

import nz.co.mircle.AbstractController;
import nz.co.mircle.profile.model.Profile;
import nz.co.mircle.profile.services.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Created by Jack on 01/06/2017.
 */

@RestController
@RequestMapping("/profile")
public class ProfileController extends AbstractController {
    private final Logger LOG = LoggerFactory.getLogger(ProfileController.class);

    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping("/create")
    public ResponseEntity createProfile(Profile profile) {
        LOG.info("Creating a new profile...");

        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            profile.setCreatedOn(currentDateTime);

            profileService.createProfile(profile);
            LOG.info("Profile " + profile.getId() + " created.");
        } catch (Exception e) {
            LOG.error("Attempt to create a new profile failed.");
            LOG.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(profile.getId(), HttpStatus.CREATED);
    }
}
