package nz.co.mircle.v1.api.user.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Friend {
    @EmbeddedId
    private FriendPK id;

    @Column(name = "addedTime")
    private LocalDateTime addedTime;

    public Friend() {
    }

    public Friend(User user, User friend, LocalDateTime addedTime) {
        this.id = new FriendPK(user, friend);
        this.addedTime = addedTime;
    }

    public FriendPK getId() {
        return id;
    }

    public void setId(FriendPK id) {
        this.id = id;
    }

    public LocalDateTime getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(LocalDateTime addedTime) {
        this.addedTime = addedTime;
    }
}
