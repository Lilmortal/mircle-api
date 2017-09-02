package nz.co.mircle.v1.api.feeds.services;

import java.util.List;
import nz.co.mircle.v1.api.feeds.dao.FeedRepository;
import nz.co.mircle.v1.api.feeds.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedServiceImpl implements FeedService {
  @Autowired private FeedRepository feedRepository;

  @Override
  public void addFeed(Feed feed) {
    feedRepository.save(feed);
  }

  @Override
  public List<Feed> getFeeds(Long id) {
    List<Feed> feeds = feedRepository.findByUserId(id);
    return feeds;
  }
}
