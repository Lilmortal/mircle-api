package nz.co.mircle.relationship.model;

import io.swagger.annotations.ApiModelProperty;
import nz.co.mircle.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Relationship primary keys.
 */
@Embeddable
public class RelationshipPK implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    @ApiModelProperty(notes = "The current user")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    @NotNull
    @ApiModelProperty(notes = "The user friend")
    private User friend;

    public RelationshipPK() {
    }

    public RelationshipPK(User user, User friend) {
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
