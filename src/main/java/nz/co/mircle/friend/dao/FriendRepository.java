package nz.co.mircle.friend.dao;

import nz.co.mircle.friend.model.Friend;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Jack on 13/06/2017.
 */
public interface FriendRepository extends CrudRepository<Friend, Long> {
}
