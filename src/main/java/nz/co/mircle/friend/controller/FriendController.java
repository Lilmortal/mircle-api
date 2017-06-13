package nz.co.mircle.friend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nz.co.mircle.AbstractController;
import nz.co.mircle.friend.model.Friend;
import nz.co.mircle.friend.services.FriendService;
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
 * Created by Jack on 13/06/2017.
 */
@RestController
@Api(value="friend", description="Friend API")
@RequestMapping("/friend")
public class FriendController extends AbstractController {
    private final Logger LOG = LoggerFactory.getLogger(FriendController.class);

    private FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @ApiOperation(value = "Create a friend", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a friend"),
            @ApiResponse(code = 201, message = "Successfully created a friend"),
            @ApiResponse(code = 401, message = "You are not authorized to create a friend."),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createFriend(@RequestBody Friend friend) {
        LOG.info("Creating a new friend...");

        try {
            friendService.createFriend(friend);
            LOG.info("Friend " + friend.getId() + " created.");
        } catch (Exception e) {
            LOG.error("Attempt to create a new friend failed.");
            LOG.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(friend.getId(), HttpStatus.CREATED);
    }
}
