package nz.co.mircle.v1.api.socialMedia.model;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/** Social media entity. */
@Entity
@Table(name = "social_media")
public class SocialMedia {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name")
  @NotNull
  @ApiModelProperty(notes = "Social media name", required = true)
  private String name;

  @Column(name = "logo")
  @ApiModelProperty(notes = "Social media logo")
  private String logo;

  public SocialMedia() {}

  public SocialMedia(String name, String logo) {
    this.name = name;
    this.logo = logo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }
}
