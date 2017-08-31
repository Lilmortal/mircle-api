package nz.co.mircle.v1.api.feeds.controller;

import com.amazonaws.AmazonServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nz.co.mircle.v1.api.feeds.model.Feed;
import nz.co.mircle.v1.api.feeds.services.FeedService;
import nz.co.mircle.v1.lib.failedResponse.model.FailedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;

@RestController
@Api(value = "feeds", description = "Feeds API")
@RequestMapping("/feeds")
public class FeedController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private FeedService feedService;

    @ApiOperation(value = "Add feed", response = Iterable.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully add feed"),
                    @ApiResponse(code = 201, message = "Successfully add feed"),
                    @ApiResponse(
                            code = 401,
                            message = "You are not authorized to add feed."
                    ),
                    @ApiResponse(
                            code = 403,
                            message = "Accessing the resource you were trying to reach is forbidden"
                    ),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            }
    )
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addFeed(@RequestBody Feed feed) {
        LOG.info("Adding feed...");

        try {
            feedService.addFeed(feed);
        } catch (AmazonServiceException e) {
            LOG.error("Failed to add feed.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOG.info("Feed successfully added.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Get feed", response = Iterable.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully add feed"),
                    @ApiResponse(code = 201, message = "Successfully add feed"),
                    @ApiResponse(
                            code = 401,
                            message = "You are not authorized to add feed."
                    ),
                    @ApiResponse(
                            code = 403,
                            message = "Accessing the resource you were trying to reach is forbidden"
                    ),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            }
    )
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity getFeeds(@RequestParam Long id) {
        LOG.info(String.format("Getting feed from user ID %d..."), id);

        List<Feed> feeds;
        try {
            feeds = feedService.getFeeds(id);
        } catch (AmazonServiceException e) {
            LOG.error("Failed to get feeds.");
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOG.info("Feeds successfully retrieved.");
        return new ResponseEntity<>(feeds, HttpStatus.OK);
    }
}
