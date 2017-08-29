package nz.co.mircle.v1.api.feeds.dao;

import nz.co.mircle.v1.api.feeds.model.Feed;
import nz.co.mircle.v1.api.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends CrudRepository<Feed, Long> {
    List<Feed> findByUserId(Long id);
}
