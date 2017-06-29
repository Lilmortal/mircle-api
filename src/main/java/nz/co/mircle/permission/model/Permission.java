package nz.co.mircle.permission.model;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import nz.co.mircle.socialMedia.model.SocialMedia;

/** Permission entity. */
@Entity
@Table(name = "permission")
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "social_media_id")
  @ApiModelProperty(notes = "Social media", required = true)
  private SocialMedia socialMedia;

  @Column(name = "has_access")
  @NotNull
  @ApiModelProperty(
    notes = "Determines whether the friend has access to this social media or not",
    required = true
  )
  private boolean hasAccess;

  // empty no arg constructor needed for hibernate
  public Permission() {}

  public Permission(SocialMedia socialMedia, boolean hasAccess) {
    this.socialMedia = socialMedia;
    this.hasAccess = hasAccess;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SocialMedia getSocialMedia() {
    return socialMedia;
  }

  public void setSocialMedia(SocialMedia socialMedia) {
    this.socialMedia = socialMedia;
  }

  public boolean isHasAccess() {
    return hasAccess;
  }

  public void setHasAccess(boolean hasAccess) {
    this.hasAccess = hasAccess;
  }
}
