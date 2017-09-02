package nz.co.mircle.v1.api.feeds.services;

import java.util.List;
import nz.co.mircle.v1.api.feeds.model.Feed;

public interface FeedService {
  void addFeed(Feed feed);

  List<Feed> getFeeds(Long id);
}
