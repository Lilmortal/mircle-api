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

  @Column(name = "type")
  @ApiModelProperty(notes = "Profile picture type")
  private String type;

  @Column(name = "name")
  @ApiModelProperty(notes = "Profile picture name")
  private String name;

  @Column(name = "default")
  @ApiModelProperty(notes = "Whether this profile picture is default or not")
  private boolean isDefault;

  public ProfileImage() {}

  public ProfileImage(URL uri, String type, String name, boolean isDefault) {
    this.uri = uri;
    this.type = type;
    this.name = name;
    this.isDefault = isDefault;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public URL getUri() {
    return uri;
  }

  public void setUri(URL uri) {
    this.uri = uri;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getIsDefault() {
    return isDefault;
  }

  public void setDefault(boolean aDefault) {
    isDefault = aDefault;
  }
}
