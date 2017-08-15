package nz.co.mircle.v1.api.user.model;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/** User entity. */
@Entity
@Table(name = "usr")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @ApiModelProperty(notes = "The database generated profile ID")
  private Long id;

  @Column(name = "email_address")
  @NotNull
  @ApiModelProperty(notes = "User email address", required = true)
  private String emailAddress;

  @Column(name = "password")
  @NotNull
  @ApiModelProperty(notes = "User password", required = true)
  private String password;

  @Column(name = "first_name")
  @NotNull
  @ApiModelProperty(notes = "User first name", required = true)
  private String firstName;

  @Column(name = "surname")
  @NotNull
  @ApiModelProperty(notes = "User surname", required = true)
  private String surname;

  @Column(name = "gender")
  @NotNull
  @ApiModelProperty(notes = "User gender", required = true)
  private String gender;

  @Column(name = "phone_number")
  @NotNull
  @ApiModelProperty(notes = "User phone number", required = true)
  private String phoneNumber;

  @Column(name = "birth_date")
  @NotNull
  @ApiModelProperty(notes = "User birth date", required = true)
  private LocalDateTime birthDate;

  @Column(name = "occupation")
  @NotNull
  @ApiModelProperty(notes = "User occupation", required = true)
  private String occupation;

  @Column(name = "created_on")
  @NotNull
  @ApiModelProperty(notes = "User creation date", required = true)
  private LocalDateTime createdOn;

  @Column(name = "last_logged_in")
  @NotNull
  @ApiModelProperty(notes = "User last logged in", required = true)
  private LocalDateTime lastLoggedIn;

  @OneToOne(cascade = CascadeType.ALL)
  private ProfileImage profileImage;

  // no args constructor needed for hibernate
  public User() {}

  public User(
      String emailAddress,
      String password,
      String firstName,
      String surname,
      String gender,
      String phoneNumber,
      LocalDateTime birthDate,
      String occupation,
      LocalDateTime createdOn,
      LocalDateTime lastLoggedIn,
      ProfileImage profileImage) {
    this.emailAddress = emailAddress;
    this.password = password;
    this.firstName = firstName;
    this.surname = surname;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.birthDate = birthDate;
    this.occupation = occupation;
    this.createdOn = createdOn;
    this.lastLoggedIn = lastLoggedIn;
    this.profileImage = profileImage;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public LocalDateTime getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDateTime birthDate) {
    this.birthDate = birthDate;
  }

  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }

  public LocalDateTime getLastLoggedIn() {
    return lastLoggedIn;
  }

  public void setLastLoggedIn(LocalDateTime lastLoggedIn) {
    this.lastLoggedIn = lastLoggedIn;
  }

  public ProfileImage getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(ProfileImage profileImage) {
    this.profileImage = profileImage;
  }

  @Override
  public String toString() {
    return "User{"
        + "id="
        + id
        + ", emailAddress='"
        + emailAddress
        + '\''
        + ", password='"
        + password
        + '\''
        + ", firstName='"
        + firstName
        + '\''
        + ", surname='"
        + surname
        + '\''
        + ", gender='"
        + gender
        + '\''
        + ", phoneNumber='"
        + phoneNumber
        + '\''
        + ", birthDate="
        + birthDate
        + ", occupation='"
        + occupation
        + '\''
        + ", createdOn="
        + createdOn
        + ", lastLoggedIn="
        + lastLoggedIn
        + ", profileImage="
        + profileImage
        + '}';
  }
}
