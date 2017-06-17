package nz.co.mircle.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nz.co.mircle.AbstractController;
import nz.co.mircle.user.model.User;
import nz.co.mircle.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Here are a lists of user API.
 */

@RestController
@Api(value="user", description="User API")
@RequestMapping("/user")
public class UserController extends AbstractController {
    private final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Create a user",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a user"),
            @ApiResponse(code = 201, message = "Successfully created a user"),
            @ApiResponse(code = 401, message = "You are not authorized to create a user."),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) {
        LOG.info("Creating a new user...");

        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            user.setCreatedOn(currentDateTime);

            userService.createUser(user);
            LOG.info("User " + user.getId() + " created.");
        } catch (Exception e) {
            LOG.error("Attempt to create a new user failed.");
            LOG.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(user.getId(), HttpStatus.CREATED);
    }
}
