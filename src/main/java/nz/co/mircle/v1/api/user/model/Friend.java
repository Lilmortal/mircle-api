package nz.co.mircle.v1.api.user.model;

import java.time.LocalDateTime;
import javax.persistence.*;
import nz.co.mircle.v1.api.profileImage.model.ProfileImage;

public class Friend {
  private Long id;

  private String emailAddress;

  private String username;

  private String firstName;

  private String surname;

  private String gender;

  private String phoneNumber;

  private LocalDateTime birthDate;

  private String occupation;

  private LocalDateTime createdOn;

  private LocalDateTime lastLoggedIn;

  private boolean loggedIn;

  private ProfileImage profileImage;

  private LocalDateTime addedTime;

  public Friend(User user, LocalDateTime addedTime) {
    this(
        user.getId(),
        user.getEmailAddress(),
        user.getUsername(),
        user.getFirstName(),
        user.getSurname(),
        user.getGender(),
        user.getPhoneNumber(),
        user.getBirthDate(),
        user.getOccupation(),
        user.getCreatedOn(),
        user.getLastLoggedIn(),
        user.isLoggedIn(),
        user.getProfileImage(),
        addedTime);
  }

  public Friend(
      Long id,
      String emailAddress,
      String username,
      String firstName,
      String surname,
      String gender,
      String phoneNumber,
      LocalDateTime birthDate,
      String occupation,
      LocalDateTime createdOn,
      LocalDateTime lastLoggedIn,
      boolean loggedIn,
      ProfileImage profileImage,
      LocalDateTime addedTime) {
    this.id = id;
    this.emailAddress = emailAddress;
    this.username = username;
    this.firstName = firstName;
    this.surname = surname;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.birthDate = birthDate;
    this.occupation = occupation;
    this.createdOn = createdOn;
    this.lastLoggedIn = lastLoggedIn;
    this.loggedIn = loggedIn;
    this.profileImage = profileImage;
    this.addedTime = addedTime;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public boolean isLoggedIn() {
    return loggedIn;
  }

  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  public ProfileImage getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(ProfileImage profileImage) {
    this.profileImage = profileImage;
  }

  public LocalDateTime getAddedTime() {
    return addedTime;
  }

  public void setAddedTime(LocalDateTime addedTime) {
    this.addedTime = addedTime;
  }
}
