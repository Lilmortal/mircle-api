package nz.co.mircle.v1.api.feeds.dao;

import java.util.List;
import nz.co.mircle.v1.api.feeds.model.Feed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends CrudRepository<Feed, Long> {
  List<Feed> findByUserId(Long id);
}
