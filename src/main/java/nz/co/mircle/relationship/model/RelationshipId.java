package nz.co.mircle.relationship.model;

import io.swagger.annotations.ApiModelProperty;
import nz.co.mircle.user.model.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jacktan on 17/06/17.
 */
@Embeddable
public class RelationshipId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private User friend;

    public RelationshipId() {
    }

    public RelationshipId(User user, User friend) {
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
