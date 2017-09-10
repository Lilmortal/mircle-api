package nz.co.mircle.v1.api.user.model;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import nz.co.mircle.v1.api.profileImage.model.ProfileImage;

/**
 * User entity.
 */
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
    @NotNull
    @ApiModelProperty(notes = "User phone number", required = false)
    private String phoneNumber;

    @Column(name = "birth_date")
    @NotNull
    @ApiModelProperty(notes = "User birth date", required = true)
    private LocalDateTime birthDate;

    @Column(name = "occupation")
    @NotNull
    @ApiModelProperty(notes = "User occupation", required = false)
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

  /*@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "friend_id")
  @ApiModelProperty(notes = "Friend", required = true)
  private User friend;

  @OneToMany(mappedBy = "friend", fetch = FetchType.LAZY)
  private List<User> friends;

  @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
  private List<Feed> feeds;*/

    // no args constructor needed for hibernate
    public User() {
    }

    public User(UserBuilder userBuilder) {
        this.emailAddress = userBuilder.emailAddress;
        this.username = userBuilder.username;
        this.password = userBuilder.password;
        this.firstName = userBuilder.firstName;
        this.surname = userBuilder.surname;
        this.gender = userBuilder.gender;
        this.phoneNumber = userBuilder.phoneNumber;
        this.birthDate = userBuilder.birthDate;
        this.occupation = userBuilder.occupation;
        this.createdOn = userBuilder.createdOn;
        this.lastLoggedIn = userBuilder.lastLoggedIn;
        this.loggedIn = userBuilder.loggedIn;
        this.profileImage = userBuilder.profileImage;
    }

    public Long getId() {
        return id;
    }

    // This is needed for testing purposes, see if there is another way to remove this
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
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

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public String getOccupation() {
        return occupation;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getLastLoggedIn() {
        return lastLoggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static UserBuilder builder(User user) {
        return new UserBuilder(user);
    }

    public static class UserBuilder {
        private Long id;
        private String emailAddress;
        private String username;
        private String password;
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

        public UserBuilder() {
        }

        public UserBuilder(User user) {
            this.id = user.id;
            this.emailAddress = user.emailAddress;
            this.username = user.username;
            this.password = user.password;
            this.firstName = user.firstName;
            this.surname = user.surname;
            this.gender = user.gender;
            this.phoneNumber = user.phoneNumber;
            this.birthDate = user.birthDate;
            this.occupation = user.occupation;
            this.createdOn = user.createdOn;
            this.lastLoggedIn = user.lastLoggedIn;
            this.loggedIn = user.loggedIn;
            this.profileImage = user.profileImage;
        }

        public UserBuilder setId(final Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setEmailAddress(final String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public UserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder setPassword(final String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setFirstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setSurname(final String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder setGender(final String gender) {
            this.gender = gender;
            return this;
        }

        public UserBuilder setPhoneNumber(final String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder setBirthDate(final LocalDateTime birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public UserBuilder setOccupation(final String occupation) {
            this.occupation = occupation;
            return this;
        }

        public UserBuilder setCreatedOn(final LocalDateTime createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public UserBuilder setLastLoggedIn(final LocalDateTime lastLoggedIn) {
            this.lastLoggedIn = lastLoggedIn;
            return this;
        }

        public UserBuilder setLoggedIn(final boolean loggedIn) {
            this.loggedIn = loggedIn;
            return this;
        }

        public UserBuilder setProfileImage(final ProfileImage profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
