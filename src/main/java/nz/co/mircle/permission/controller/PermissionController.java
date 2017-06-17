package nz.co.mircle.permission.controller;

import nz.co.mircle.AbstractController;
import nz.co.mircle.permission.model.Permission;
import nz.co.mircle.permission.services.PermissionService;
import nz.co.mircle.socialMedia.model.SocialMedia;
import nz.co.mircle.socialMedia.services.SocialMediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Here are a lists of permissions API.
 */
@RestController
@RequestMapping("/permission")
public class PermissionController extends AbstractController {
    private final Logger LOG = LoggerFactory.getLogger(PermissionController.class);

    private PermissionService permissionService;

    private SocialMediaService socialMediaService;

    @Autowired
    public PermissionController(PermissionService permissionService, SocialMediaService socialMediaService) {
        this.permissionService = permissionService;
        this.socialMediaService = socialMediaService;
    }

    @RequestMapping(name = "/create", method = RequestMethod.POST)
    public ResponseEntity createPermission(@RequestParam("socialMediaId") Long socialMediaId, @RequestParam("hasAccess") boolean hasAccess) {
        LOG.info("Creating a permission...");

        try {
            SocialMedia socialMedia = socialMediaService.findSocialMedia(socialMediaId);
            Permission permission = new Permission(socialMedia, hasAccess);
            permissionService.createPermission(permission);
            LOG.info("Permission created.");
        } catch (Exception e) {
            LOG.error("Permission failed to be created.");
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
