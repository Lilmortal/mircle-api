package nz.co.mircle.v1.api.user.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name ="user_friend")
@AssociationOverrides({
        @AssociationOverride(name ="pk.user", joinColumns = @JoinColumn(name ="user_id")),
        @AssociationOverride(name ="pk.friend", joinColumns = @JoinColumn(name ="friend_id"))
})
public class Friend {
    @EmbeddedId
    private FriendPK pk;

    @Column(name = "addedTime")
    private LocalDateTime addedTime;

    public Friend() {
    }

    public Friend(User user, User friend, LocalDateTime addedTime) {
        this.pk = new FriendPK(user, friend);
        this.addedTime = addedTime;
    }

    public FriendPK getPk() {
        return pk;
    }

    public void setPk(FriendPK pk) {
        this.pk = pk;
    }

    public LocalDateTime getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(LocalDateTime addedTime) {
        this.addedTime = addedTime;
    }
}
