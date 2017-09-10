package nz.co.mircle.v1.api.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nz.co.mircle.v1.api.profileImage.model.ProfileImage;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * User controller test
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    private static final Long ID = Long.valueOf("1");

    private static final String EMAIL_ADDRESS = "email@email.com";

    private static final String USERNAME = "username";

    private static final String PASSWORD = "abcdef";

    private static final String FIRST_NAME = "firstName";

    private static final String SURNAME = "surname";

    private static final String GENDER = "M";

    private static final String PHONE_NUMBER = "123456";

    private static final LocalDateTime BIRTH_DATE = LocalDateTime.now().withNano(0);

    private static final String OCCUPATION = "Programmer";

    private static final LocalDateTime CREATED_ON = LocalDateTime.now().withNano(0);

    private static final LocalDateTime LAST_LOGGED_IN = LocalDateTime.now().withNano(0);

    private static final boolean IS_LOGGED_IN = true;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private URL profileImageUrl;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProfileImageService profileImageService;

    @MockBean
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> updatedUserCaptor;

    @Mock
    private ProfileImage profileImage;

    @Before
    public void setup() throws MalformedURLException {
        profileImageUrl = new URL("http://test.com");
        when(profileImage.getUri()).thenReturn(profileImageUrl);
    }

    @Test
    public void givenIdThenReturnUser() throws Exception {
        User user = populateUser().build();

        when(userService.findUser(ID)).thenReturn(user);

        MvcResult result = mvc.perform(get(String.format("/user/%d", ID)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = getJsonResult(user);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(jsonResult);
    }

    @Test
    public void givenInvalidIdWhenFindingUserShouldThrowUserNotFound() throws Exception {
        when(userService.findUser(ID)).thenThrow(UsernameNotFoundException.class);

        mvc.perform(get(String.format("/user/%d", ID)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void givenEmailAddressThenReturnUser() throws Exception {
        User user = populateUser().build();

        when(userService.findUser(EMAIL_ADDRESS)).thenReturn(user);

        MvcResult result = mvc.perform(get(String.format("/user/email/%s", EMAIL_ADDRESS)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = getJsonResult(user);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(jsonResult);
    }

    @Test
    public void givenInvalidEmailAddressWhenFindingUserShouldThrowUserNotFound() throws Exception {
        when(userService.findUser(EMAIL_ADDRESS)).thenThrow(UsernameNotFoundException.class);

        mvc.perform(get(String.format("/user/email/%s", EMAIL_ADDRESS)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void givenGenderThenUpdateUser() throws Exception {
        String gender = "F";

        User user = populateUser().build();
        when(userService.findUser(EMAIL_ADDRESS)).thenReturn(user);
        doNothing().when(userService).saveUser(updatedUserCaptor.capture());

        mvc.perform(patch(String.format("/user?emailAddress=%s&gender=%s", EMAIL_ADDRESS, gender)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(updatedUserCaptor.getValue().getGender()).isEqualTo(gender);
    }

    @Test
    public void givenPhoneNumberThenUpdateUser() throws Exception {
        String phoneNumber = "123";

        User user = populateUser().build();
        when(userService.findUser(EMAIL_ADDRESS)).thenReturn(user);
        doNothing().when(userService).saveUser(updatedUserCaptor.capture());

        mvc.perform(patch(String.format("/user?emailAddress=%s&phoneNumber=%s", EMAIL_ADDRESS, phoneNumber)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(updatedUserCaptor.getValue().getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    public void givenBirthDateThenUpdateUser() throws Exception {
        String birthDate = "23/08/1992";
        LocalDateTime parsedDateTime = LocalDate.parse(birthDate, FORMATTER).atStartOfDay();

        User user = populateUser().build();
        when(userService.findUser(EMAIL_ADDRESS)).thenReturn(user);
        doNothing().when(userService).saveUser(updatedUserCaptor.capture());

        mvc.perform(patch(String.format("/user?emailAddress=%s&birthDate=%s", EMAIL_ADDRESS, birthDate)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(updatedUserCaptor.getValue().getBirthDate()).isEqualTo(parsedDateTime);
    }

    @Test
    public void givenOccupationThenUpdateUser() throws Exception {
        String occupation = "Doctor";

        User user = populateUser().build();
        when(userService.findUser(EMAIL_ADDRESS)).thenReturn(user);
        doNothing().when(userService).saveUser(updatedUserCaptor.capture());

        mvc.perform(patch(String.format("/user?emailAddress=%s&occupation=%s", EMAIL_ADDRESS, occupation)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(updatedUserCaptor.getValue().getOccupation()).isEqualTo(occupation);
    }

    @Test
    public void givenIdThenUpdateUserPassword() throws Exception {
        String newPassword = "cvb";

        User user = populateUser().build();

        when(userService.findUser(ID)).thenReturn(user);
        doNothing().when(userService).changePassword(user, PASSWORD, newPassword);
        mvc.perform(patch(String.format("/user/password?id=%d&oldPassword=%s&newPassword=%s", ID, PASSWORD, newPassword)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void givenEmailAddressThenUpdateUserPassword() throws Exception {
        String newPassword = "cvb";

        User user = populateUser().build();

        when(userService.findUser(EMAIL_ADDRESS)).thenReturn(user);
        doNothing().when(userService).changePassword(user, PASSWORD, newPassword);
        mvc.perform(patch(String.format("/user/password?emailAddress=%s&oldPassword=%s&newPassword=%s", EMAIL_ADDRESS, PASSWORD, newPassword)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void givenIdAndNoProfileImageThenUpdateUserProfileImageToDefault() throws Exception {
        User user = populateUser().build();

        when(userService.findUser(ID)).thenReturn(user);
        when(profileImageService.getDefaultImage()).thenReturn(profileImageUrl);
        doNothing().when(userService).setUserProfileImage(user, profileImageUrl);
        mvc.perform(patch(String.format("/user/profileimage?id=%d", ID)))
                .andExpect(status().isOk())
                .andReturn();
    }

    //TODO
    @Test
    public void givenEmailAddressAndProfileImageThenUpdateUserProfileImage() throws Exception {
        User user = populateUser().build();

        when(userService.findUser(EMAIL_ADDRESS)).thenReturn(user);
        when(profileImageService.getDefaultImage()).thenReturn(profileImageUrl);
        doNothing().when(userService).setUserProfileImage(user, profileImageUrl);
        mvc.perform(patch(String.format("/user/profileimage?id=%d", ID)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void givenIdThenDeleteUser() throws Exception {
        User user = populateUser().build();

        when(userService.findUser(ID)).thenReturn(user);
        doNothing().when(userService).deleteUser(user);
        mvc.perform(delete(String.format("/user/%d", ID)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void givenEmailAddressThenDeleteUser() throws Exception {
        User user = populateUser().build();

        when(userService.findUser(EMAIL_ADDRESS)).thenReturn(user);
        doNothing().when(userService).deleteUser(user);
        mvc.perform(delete(String.format("/user/email/%s", EMAIL_ADDRESS)))
                .andExpect(status().isOk())
                .andReturn();
    }

    private User.UserBuilder populateUser() {
        return User.builder()
                .setId(ID)
                .setEmailAddress(EMAIL_ADDRESS)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setFirstName(FIRST_NAME)
                .setSurname(SURNAME)
                .setGender(GENDER)
                .setPhoneNumber(PHONE_NUMBER)
                .setBirthDate(BIRTH_DATE)
                .setOccupation(OCCUPATION)
                .setCreatedOn(CREATED_ON)
                .setLastLoggedIn(LAST_LOGGED_IN)
                .setLoggedIn(IS_LOGGED_IN)
                .setProfileImage(profileImage);
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
