package nz.co.mircle.v1.api.user.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import nz.co.mircle.v1.api.profileImage.model.ProfileImage;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.user.dao.UserRepository;
import nz.co.mircle.v1.api.user.model.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

/**
 * User service layer test
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class UserServiceImplTest {
    private static final Long ID = Long.parseLong("1");

    private static final String EMAIL_ADDRESS = "test@test.com";

    private static final String USERNAME = "username";

    private static final String PASSWORD = "password";

    private static final String FIRST_NAME = "First_name";

    private static final String SURNAME = "surname";

    private static final String GENDER = "M";

    private static final String PHONE_NUMBER = "12345";

    private static final LocalDateTime BIRTH_DATE = LocalDateTime.now();

    private static final String OCCUPATION = "Engineer";

    private static final LocalDateTime CREATED_ON = LocalDateTime.now();

    private static final LocalDateTime LAST_LOGGED_IN = LocalDateTime.now();

    private static final boolean IS_LOGGED_IN = false;

    private ProfileImage profileImage;

    private URL url;

    private User user;

    @MockBean
    private ProfileImageService profileImageService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @InjectMocks
    private UserService userService = new UserServiceImpl(profileImageService, userRepository, encoder);

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setup() throws MalformedURLException {
        url = new URL("http://test.com");
        profileImage = new ProfileImage(url, "type", "name", true);
        user = populateUser();
    }

    @Test
    public void givenIdReturnUser() {
        when(userRepository.findOne(ID)).thenReturn(user);

        User result = userService.findUser(ID);
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void givenEmailAddressReturnUser() {
        when(userRepository.findByEmailAddress(EMAIL_ADDRESS)).thenReturn(user);

        User result = userService.findUser(EMAIL_ADDRESS);
        assertThat(result).isEqualTo(user);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void givenInvalidEmailAddressReturnUser() {
        User result = userService.findUser("Invalid Email Address");
    }

    @Test
    public void setUserInitialProfileImage() {
        user.setProfileImage(null);
        assertThat(user.getProfileImage()).isEqualTo(null);
        when(userRepository.save(userCaptor.capture())).thenReturn(user);

        User result = userService.setUserProfileImage(user, url);
        assertThat(userCaptor.getValue().getProfileImage().getUri()).isEqualTo(url);
    }

    @Test
    public void changeUserPassword() {
        String oldPassword = "abc";
        String newPassword = "qwe";
        String encodedPassword = "qqqqq";

        when(encoder.matches(oldPassword, user.getPassword())).thenReturn(true);
        when(encoder.encode(newPassword)).thenReturn(encodedPassword);
        when(userRepository.save(userCaptor.capture())).thenReturn(user);

        User result = userService.changePassword(user, oldPassword, newPassword);
        assertThat(userCaptor.getValue().getPassword()).isEqualTo(encodedPassword);
    }

    @Test(expected = RuntimeException.class)
    public void givenUserPasswordNotEqualToOldPasswordThrowException() {
        String oldPassword = "abc";
        String newPassword = "qwe";
        String encodedPassword = "qqqqq";

        when(encoder.matches(oldPassword, user.getPassword())).thenReturn(false);

        User result = userService.changePassword(user, oldPassword, newPassword);
    }

    private User populateUser() {
        User user = new User(EMAIL_ADDRESS, PASSWORD, FIRST_NAME, SURNAME, GENDER, PHONE_NUMBER, BIRTH_DATE, OCCUPATION, CREATED_ON, LAST_LOGGED_IN, IS_LOGGED_IN, profileImage, null, null);
        user.setId(ID);
        user.setUsername(USERNAME);
        return user;
    }

    private String getJsonResult(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(String.format("\"id\":%d,", user.getId()));
        sb.append(String.format("\"emailAddress\":\"%s\",", user.getEmailAddress()));
        sb.append(String.format("\"username\":\"%s\",", user.getUsername()));
        sb.append(String.format("\"password\":\"%s\",", user.getPassword()));
        sb.append(String.format("\"firstName\":\"%s\",", user.getFirstName()));
        sb.append(String.format("\"surname\":\"%s\",", user.getSurname()));
        sb.append(String.format("\"gender\":\"%s\",", user.getGender()));
        sb.append(String.format("\"phoneNumber\":\"%s\",", user.getPhoneNumber()));
        sb.append(String.format("\"birthDate\":\"%s\",", user.getBirthDate()));
        sb.append(String.format("\"occupation\":\"%s\",", user.getOccupation()));
        sb.append(String.format("\"createdOn\":\"%s\",", user.getCreatedOn()));
        sb.append(String.format("\"lastLoggedIn\":\"%s\",", user.getLastLoggedIn()));
        sb.append(String.format("\"loggedIn\":%b,", user.isLoggedIn()));
        sb.append(String.format("\"profileImage\":%s", user.getProfileImage()));
        sb.append(String.format("\"friends\":%s", user.getUserFriends()));
        sb.append(String.format("\"feeds\":%s", user.getFeeds()));
        sb.append("}");
        return sb.toString();
    }
}
