package nz.co.mircle.userSocialMedia.model;

import nz.co.mircle.socialMedia.model.SocialMedia;
import nz.co.mircle.user.model.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jacktan on 17/06/17.
 */
@Embeddable
public class UserSocialMediaPK implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "social_media_id")
    private SocialMedia socialMedia;

    public UserSocialMediaPK() {
    }

    public UserSocialMediaPK(User user, SocialMedia socialMedia) {
        this.user = user;
        this.socialMedia = socialMedia;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SocialMedia getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(SocialMedia socialMedia) {
        this.socialMedia = socialMedia;
    }
}
