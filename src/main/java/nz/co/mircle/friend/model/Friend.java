package nz.co.mircle.friend.model;

import io.swagger.annotations.ApiModelProperty;
import nz.co.mircle.profile.model.Profile;

import javax.persistence.*;

/**
 * Created by Jack on 13/06/2017.
 */
@Entity
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated friend ID")
    private Long id;

    @Column(name = "friend_id")
    private Long friendId;

    @ManyToOne(cascade = CascadeType.ALL)
    private Profile profile;

    @Column(name = "permission")
    @ApiModelProperty(notes = "Friend permission", required = true)
    private short permission;

    // Needs no arg constructor for hibernate
    public Friend() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public short getPermission() {
        return permission;
    }

    public void setPermission(short permission) {
        this.permission = permission;
    }
}
