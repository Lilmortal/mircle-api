package nz.co.mircle.userSocialMedia.model;

import nz.co.mircle.socialMedia.model.SocialMedia;
import nz.co.mircle.user.model.User;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User social media entity.
 */
@Entity
@Table(name = "user_social_media")
public class UserSocialMedia {
    @EmbeddedId
    private UserSocialMediaPK id;

    @Column(name = "url")
    private String url;

    public UserSocialMedia() {
    }

    public UserSocialMedia(User user, SocialMedia socialMedia, String url) {
        this.id = new UserSocialMediaPK(user, socialMedia);
        this.url = url;
    }

    public UserSocialMediaPK getId() {
        return id;
    }

    public void setId(UserSocialMediaPK id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
