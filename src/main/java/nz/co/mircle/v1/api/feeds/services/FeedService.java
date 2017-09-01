package nz.co.mircle.v1.api.feeds.services;

import nz.co.mircle.v1.api.feeds.model.Feed;

import java.util.List;

public interface FeedService {
    void addFeed(Feed feed);

    List<Feed> getFeeds(Long id);
}
