package nz.co.mircle.v1.api.user.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FriendPK implements Serializable {
    @Column(name = "user")
    private User user;

    @Column(name = "friend")
    private User friend;

    public FriendPK() {
    }

    public FriendPK(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
