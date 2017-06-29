package nz.co.mircle.user.model;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
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

  @Column(name = "username")
  @NotNull
  @ApiModelProperty(notes = "User username", required = true)
  private String username;

  @Column(name = "password")
  @NotNull
  @ApiModelProperty(notes = "User password", required = true)
  private String password;

  @Column(name = "first_name")
  @NotNull
  @ApiModelProperty(notes = "User first name", required = true)
  private String firstName;

  @Column(name = "last_name")
  @NotNull
  @ApiModelProperty(notes = "User last name", required = true)
  private String lastName;

  @Column(name = "gender")
  @NotNull
  @ApiModelProperty(notes = "User gender", required = true)
  private String gender;

  @Column(name = "email_address")
  @NotNull
  @ApiModelProperty(notes = "User email address", required = true)
  private String emailAddress;

  @Column(name = "phone_number")
  @NotNull
  @ApiModelProperty(notes = "User phone number", required = true)
  private String phoneNumber;

  @Column(name = "birth_date")
  @NotNull
  @ApiModelProperty(notes = "User birth date", required = true)
  private LocalDate birthDate;

  @Column(name = "created_on")
  @NotNull
  @ApiModelProperty(notes = "User creation date", required = true)
  private LocalDateTime createdOn;

  @Column(name = "last_logged_in")
  @NotNull
  @ApiModelProperty(notes = "User last logged in", required = true)
  private LocalDateTime lastLoggedIn;

  // no args constructor needed for hibernate
  public User() {}

  public User(
      String username,
      String password,
      String firstName,
      String lastName,
      String gender,
      String emailAddress,
      String phoneNumber,
      LocalDate birthDate,
      LocalDateTime createdOn,
      LocalDateTime lastLoggedIn) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.emailAddress = emailAddress;
    this.phoneNumber = phoneNumber;
    this.birthDate = birthDate;
    this.createdOn = createdOn;
    this.lastLoggedIn = lastLoggedIn;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
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
}
