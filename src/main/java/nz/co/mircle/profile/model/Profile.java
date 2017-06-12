package nz.co.mircle.profile.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Jack on 01/06/2017.
 */
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated profile ID")
    private Long id;

    @Column(name = "username")
    @ApiModelProperty(notes = "Profile username")
    private String username;

    @Column(name = "password")
    @ApiModelProperty(notes = "Profile password")
    private String password;

    @Column(name = "first_name")
    @ApiModelProperty(notes = "Profile first name")
    private String firstName;

    @Column(name = "last_name")
    @ApiModelProperty(notes = "Profile last name")
    private String lastName;

    @Column(name = "gender")
    @ApiModelProperty(notes = "Profile gender")
    private String gender;

    @Column(name = "email_address")
    @ApiModelProperty(notes = "Profile email address")
    private String emailAddress;

    @Column(name = "phone_number")
    @ApiModelProperty(notes = "Profile phone number")
    private String phoneNumber;

    @Column(name = "birth_date")
    @ApiModelProperty(notes = "Profile birth date")
    private LocalDate birthDate;

    @Column(name = "created_on")
    @ApiModelProperty(notes = "Profile creation date")
    private LocalDateTime createdOn;

    @Column(name = "last_logged_in")
    @ApiModelProperty(notes = "Profile last logged in")
    private LocalDateTime lastLoggedIn;

    // no args constructor needed for hibernate
    public Profile() {
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

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", createdOn=" + createdOn +
                ", lastLoggedIn=" + lastLoggedIn +
                '}';
    }

    public void setLastLoggedIn(LocalDateTime lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }
}
