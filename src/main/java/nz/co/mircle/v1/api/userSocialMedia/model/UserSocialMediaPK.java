package nz.co.mircle.v1.api.userSocialMedia.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import nz.co.mircle.v1.api.socialMedia.model.SocialMedia;
import nz.co.mircle.v1.api.user.model.User;

/** User social media primary keys. */
@Embeddable
public class UserSocialMediaPK implements Serializable {
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @NotNull
  @ApiModelProperty(notes = "The current user", required = true)
  private User user;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "social_media_id")
  @NotNull
  @ApiModelProperty(notes = "User social media", required = true)
  private SocialMedia socialMedia;

  public UserSocialMediaPK() {}

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