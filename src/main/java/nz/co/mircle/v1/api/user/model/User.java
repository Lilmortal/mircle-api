package nz.co.mircle.v1.api.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import nz.co.mircle.v1.api.feeds.model.Feed;
import nz.co.mircle.v1.api.profileImage.model.ProfileImage;

/**
 * User entity.
 */
@Entity
@Table(name = "usr")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated profile ID")
    private Long id;

    @Column(name = "email_address")
    @NotNull
    @ApiModelProperty(notes = "User email address", required = true)
    private String emailAddress;

    @Column(name = "username")
    @NotNull
    @ApiModelProperty(notes = "Username", required = true)
    private String username;

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
    @ApiModelProperty(notes = "User phone number")
    private String phoneNumber;

    @Column(name = "birth_date")
    @NotNull
    @ApiModelProperty(notes = "User birth date", required = true)
    private LocalDateTime birthDate;

    @Column(name = "occupation")
    @ApiModelProperty(notes = "User occupation")
    private String occupation;

    @Column(name = "created_on")
    @NotNull
    @ApiModelProperty(notes = "User creation date", required = true)
    private LocalDateTime createdOn;

    @Column(name = "last_logged_in")
    @NotNull
    @ApiModelProperty(notes = "User last logged in", required = true)
    private LocalDateTime lastLoggedIn;

    @Column(name = "is_logged_in")
    @NotNull
    @ApiModelProperty(notes = "Is user currently logged in", required = true)
    private boolean loggedIn;

    @OneToOne(cascade = CascadeType.ALL)
    private ProfileImage profileImage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Set<Feed> feeds;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY/*, mappedBy = "pk.friend"*/)
    @JoinColumn(name = "user_id")
    private Set<Friend> friends;

    // no args constructor needed for hibernate
    public User() {
    }

    public User(String emailAddress, String password, String firstName, String surname, String gender, String phoneNumber, LocalDateTime birthDate, String occupation, LocalDateTime createdOn, LocalDateTime lastLoggedIn, boolean loggedIn, ProfileImage profileImage, Set<Friend> friends, Set<Feed> feeds) {
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
        this.loggedIn = loggedIn;
        this.profileImage = profileImage;
        this.friends = friends;
        this.feeds = feeds;
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

    public Set<Friend> getFriends() {
        return friends;
    }

    public void setFriends(Set<Friend> friends) {
        this.friends = friends;
    }

    public Set<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(Set<Feed> feeds) {
        this.feeds = feeds;
    }
}
