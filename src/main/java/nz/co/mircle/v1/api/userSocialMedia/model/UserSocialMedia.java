package nz.co.mircle.v1.api.userSocialMedia.model;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nz.co.mircle.v1.api.socialMedia.model.SocialMedia;
import nz.co.mircle.v1.api.user.model.User;

/** User social media entity. */
@Entity
@Table(name = "user_social_media")
public class UserSocialMedia {
  @EmbeddedId private UserSocialMediaPK id;

  @Column(name = "url")
  @ApiModelProperty(notes = "User social media URL")
  private String url;

  public UserSocialMedia() {}

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
