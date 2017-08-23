package nz.co.mircle.v1.api.profileImage.model;

import io.swagger.annotations.ApiModelProperty;
import java.net.URL;
import javax.persistence.*;

/** Created by tanj1 on 16/08/2017. */
@Entity
@Table(name = "profile_image")
public class ProfileImage {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @ApiModelProperty(notes = "The database generated profile image ID")
  private Long id;

  @Column(name = "uri")
  @ApiModelProperty(notes = "Profile picture uri link")
  private URL uri;

  @Column(name = "default")
  @ApiModelProperty(notes = "Whether this profile picture is default or not")
  private boolean isDefault;

  public ProfileImage() {}

  public ProfileImage(URL uri, boolean isDefault) {
    this.uri = uri;
    this.isDefault = isDefault;
  }

  public Long getId() {
    return id;
  }

  public URL getUri() {
    return uri;
  }

  public void setUri(URL uri) {
    this.uri = uri;
  }

  public boolean isDefault() {
    return isDefault;
  }

  public void setIsDefault(boolean isDefault) {
    this.isDefault = isDefault;
  }

  @Override
  public String toString() {
    return "ProfileImage{" + "id=" + id + ", uri='" + uri + '\'' + ", isDefault=" + isDefault + '}';
  }
}
