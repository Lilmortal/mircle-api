package nz.co.mircle.v1.api.iam.services;

import nz.co.mircle.v1.api.iam.exception.EmailAddressExistException;
import nz.co.mircle.v1.api.profileImage.model.ProfileImage;
import nz.co.mircle.v1.api.user.dao.UserRepository;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class AuthenticationServiceImplTest {
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

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService service = new AuthenticationServiceImpl(encoder, userService, userRepository);

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private ProfileImage profileImage;

    private URL url;

    private User user;

    @Before
    public void setup() throws MalformedURLException {
        url = new URL("http://test.com");
        profileImage = new ProfileImage(url, "type", "name", true);
        user = populateUser();
    }

    @Test
    public void createUser() {
        when(userService.findUser(EMAIL_ADDRESS)).thenThrow(UsernameNotFoundException.class);
        when(encoder.encode(PASSWORD)).thenReturn(PASSWORD);
        when(userRepository.save(user)).thenReturn(user);

        service.createUser(user);
    }

    @Test(expected = EmailAddressExistException.class)
    public void givenAnExistingUserThrowAnEmailAddressExistException() {
        when(userService.findUser(EMAIL_ADDRESS)).thenReturn(user);

        service.createUser(user);
    }

    @Test
    public void givenUsernameReturnUser() {
        org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(USERNAME, PASSWORD, emptyList());

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        UserDetails result = service.loadUserByUsername(USERNAME);
        assertThat(result).isEqualTo(securityUser);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void givenInvalidUsernameThrowUsernameNotFoundException() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(null);

        service.loadUserByUsername(USERNAME);
    }

    private User populateUser() {
        User user = new User(EMAIL_ADDRESS, PASSWORD, FIRST_NAME, SURNAME, GENDER, PHONE_NUMBER, BIRTH_DATE, OCCUPATION, CREATED_ON, LAST_LOGGED_IN, IS_LOGGED_IN, profileImage);
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
        sb.append("}");
        return sb.toString();
    }
}
