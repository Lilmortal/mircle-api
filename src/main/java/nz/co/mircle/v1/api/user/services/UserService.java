package nz.co.mircle.v1.api.user.services;

import java.net.URL;
import java.util.Set;

import nz.co.mircle.v1.api.feeds.model.Feed;
import nz.co.mircle.v1.api.user.model.Friend;
import nz.co.mircle.v1.api.user.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/** Lists of services that can be used to call the user repository. */
public interface UserService {
  User findUser(Long id) throws UsernameNotFoundException;

  User findUser(String emailAddress) throws UsernameNotFoundException;

  User saveUser(User user);

  User setUserProfileImage(User user, URL profileImage);

  User changePassword(User user, String oldPassword, String newPassword);

  void deleteUser(User user);

  Friend addFriend(User user, User friend);

  Set<Friend> findFriends(Long id);

  void deleteFriend(Long id, Long friendId);

  void addFeed(Long id, Feed feed);

  Set<Feed> findFeeds(Long id);
}
