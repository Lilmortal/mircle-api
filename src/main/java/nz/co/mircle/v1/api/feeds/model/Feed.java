package nz.co.mircle.v1.api.feeds.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import nz.co.mircle.v1.api.profileImage.model.ProfileImage;
import nz.co.mircle.v1.api.user.model.User;

@Entity
@Table(name = "feeds")
public class Feed implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @ApiModelProperty(notes = "The database generated feed ID")
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  private ProfileImage profileImage;

  @Column(name = "first_name")
  @ApiModelProperty(notes = "First name")
  private String firstName;

  @Column(name = "surname")
  @ApiModelProperty(notes = "First name")
  private String surname;

  @Column(name = "feed_date")
  @ApiModelProperty(notes = "First name")
  private LocalDateTime feedDate;

  @Column(name = "message")
  @ApiModelProperty(notes = "First name")
  private String message;

  @OneToOne(cascade = CascadeType.ALL)
  private User user;

  public Feed() {}

  public Feed(
      ProfileImage profileImage,
      String firstName,
      String surname,
      LocalDateTime feedDate,
      String message,
      User user) {
    this.profileImage = profileImage;
    this.firstName = firstName;
    this.surname = surname;
    this.feedDate = feedDate;
    this.message = message;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ProfileImage getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(ProfileImage profileImage) {
    this.profileImage = profileImage;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public LocalDateTime getFeedDate() {
    return feedDate;
  }

  public void setFeedDate(LocalDateTime feedDate) {
    this.feedDate = feedDate;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
