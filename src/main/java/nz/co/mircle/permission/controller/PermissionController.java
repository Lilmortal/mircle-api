package nz.co.mircle.permission.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Here are a lists of permissions API.
 */
@RestController
@Api(value="permission", description="Permission API")
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

    @ApiOperation(value = "Create a permission",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a permission"),
            @ApiResponse(code = 201, message = "Successfully created a permission"),
            @ApiResponse(code = 401, message = "You are not authorized to create a permission."),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createPermission(@RequestParam("socialMediaId") Long socialMediaId, @RequestParam("hasAccess") boolean hasAccess) {
        LOG.info("Creating a permission...");

        try {
            SocialMedia socialMedia = socialMediaService.findSocialMedia(socialMediaId);
            Permission permission = new Permission(socialMedia, hasAccess);
            permissionService.createPermission(permission);
            LOG.info("Permission created.");
        } catch (Exception e) {
            LOG.error("Attempt to create a new permission failed.");
            LOG.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
