package nz.co.mircle.relationship.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nz.co.mircle.AbstractController;
import nz.co.mircle.permission.model.Permission;
import nz.co.mircle.permission.services.PermissionService;
import nz.co.mircle.relationship.model.Relationship;
import nz.co.mircle.relationship.services.RelationshipService;
import nz.co.mircle.socialMedia.model.SocialMedia;
import nz.co.mircle.socialMedia.services.SocialMediaService;
import nz.co.mircle.user.model.User;
import nz.co.mircle.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jack on 13/06/2017.
 */
@RestController
@Api(value="friend", description="Relationship API")
@RequestMapping("/friend")
public class RelationshipController extends AbstractController {
    private final Logger LOG = LoggerFactory.getLogger(RelationshipController.class);

    private RelationshipService relationshipService;

    private UserService userService;

    private SocialMediaService socialMediaService;

    private PermissionService permissionService;

    @Autowired
    public RelationshipController(RelationshipService relationshipService, UserService userService, SocialMediaService socialMediaService, PermissionService permissionService) {
        this.relationshipService = relationshipService;
        this.userService = userService;
        this.socialMediaService = socialMediaService;
        this.permissionService = permissionService;
    }

    @ApiOperation(value = "Create a relationship", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a relationship"),
            @ApiResponse(code = 201, message = "Successfully created a relationship"),
            @ApiResponse(code = 401, message = "You are not authorized to create a relationship."),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity createRelationship(@RequestParam("userId") String userId, @RequestParam("friendId") String friendId, @RequestParam("socialMediaTitle") String socialMediaTitle, @RequestParam("hasAccess") boolean hasAccess) {
        LOG.info("Creating a new relationship...");

        try {
            User user = userService.findUser(Long.parseLong(userId));
            User friend = userService.findUser(Long.parseLong(friendId));
            SocialMedia socialMedia = socialMediaService.findSocialMedia(socialMediaTitle);
            Permission permission = permissionService.findPermission(socialMedia, hasAccess);

            Relationship relationship = new Relationship(user, friend, permission);
            relationshipService.createRelationship(relationship);
            LOG.info("Relationship created.");
        } catch (Exception e) {
            LOG.error("Attempt to create a new relationship failed.");
            LOG.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
