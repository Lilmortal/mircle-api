package nz.co.mircle.v1.api.user.model;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/** Created by tanj1 on 11/08/2017. */
@Entity
@Table(name = "profile_image")
public class ProfileImage {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @ApiModelProperty(notes = "The database generated profile image ID")
  private Long id;

  @Column(name = "default")
  @ApiModelProperty(
    notes = "Determine if the current profile picture is the default picture",
    required = true
  )
  private boolean isDefaultImage;

  @OneToOne(cascade = CascadeType.ALL)
  private Image image;

  @Column(name = "mime")
  @ApiModelProperty(notes = "Profile picture mime")
  private String mime;

  @Column(name = "size")
  @ApiModelProperty(notes = "Profile picture size")
  private Long size;

  @Column(name = "width")
  @ApiModelProperty(notes = "Profile picture width")
  private Long width;

  @Column(name = "height")
  @ApiModelProperty(notes = "Profile picture height")
  private Long height;

  public ProfileImage() {}

  public ProfileImage(
      boolean isDefaultImage,
      Image image,
      String mime,
      Long size,
      Long width,
      Long height) {
    this.isDefaultImage = isDefaultImage;
    this.image = image;
    this.mime = mime;
    this.size = size;
    this.width = width;
    this.height = height;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isDefaultImage() {
    return isDefaultImage;
  }

  public void setDefaultImage(boolean defaultImage) {
    isDefaultImage = defaultImage;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public String getMime() {
    return mime;
  }

  public void setMime(String mime) {
    this.mime = mime;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public Long getWidth() {
    return width;
  }

  public void setWidth(Long width) {
    this.width = width;
  }

  public Long getHeight() {
    return height;
  }

  public void setHeight(Long height) {
    this.height = height;
  }

  @Override
  public String toString() {
    return "ProfileImage{"
        + "isDefaultImage="
        + isDefaultImage
        + ", image="
        + image
        + ", mime='"
        + mime
        + '\''
        + ", size="
        + size
        + ", width="
        + width
        + ", height="
        + height
        + '}';
  }
}
