package nz.co.mircle.v1.api.relationship.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import nz.co.mircle.v1.api.user.model.User;

/** Relationship primary keys. */
@Embeddable
public class RelationshipPK implements Serializable {
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @ApiModelProperty(notes = "The current user", required = true)
  private User user;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "friend_id")
  @ApiModelProperty(notes = "The user friend", required = true)
  private User friend;

  public RelationshipPK() {}

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