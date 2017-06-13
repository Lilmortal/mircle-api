package nz.co.mircle.profile.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import nz.co.mircle.friend.model.Friend;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by Jack on 01/06/2017.
 */
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated profile ID")
    private Long id;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<Friend> friends;

    @Column(name = "username")
    @ApiModelProperty(notes = "Profile username", required = true)
    private String username;

    @Column(name = "password")
    @ApiModelProperty(notes = "Profile password", required = true)
    private String password;

    @Column(name = "first_name")
    @ApiModelProperty(notes = "Profile first name", required = true)
    private String firstName;

    @Column(name = "last_name")
    @ApiModelProperty(notes = "Profile last name", required = true)
    private String lastName;

    @Column(name = "gender")
    @ApiModelProperty(notes = "Profile gender", required = true)
    private String gender;

    @Column(name = "email_address")
    @ApiModelProperty(notes = "Profile email address", required = true)
    private String emailAddress;

    @Column(name = "phone_number")
    @ApiModelProperty(notes = "Profile phone number", required = true)
    private String phoneNumber;

    @Column(name = "birth_date")
    @ApiModelProperty(notes = "Profile birth date", required = true)
    private LocalDate birthDate;

    @Column(name = "created_on")
    @ApiModelProperty(notes = "Profile creation date", required = true)
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

    public Set<Friend> getFriends() {
        return friends;
    }

    public void setFriends(Set<Friend> friends) {
        this.friends = friends;
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
