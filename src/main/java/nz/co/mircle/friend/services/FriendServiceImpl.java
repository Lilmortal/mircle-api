package nz.co.mircle.friend.services;

import nz.co.mircle.friend.dao.FriendRepository;
import nz.co.mircle.friend.model.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jack on 13/06/2017.
 */
@Service
public class FriendServiceImpl implements FriendService {
    private FriendRepository friendRepository;

    @Autowired
    public FriendServiceImpl(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Override
    public void createFriend(Friend friend) {
        friendRepository.save(friend);
    }
}
