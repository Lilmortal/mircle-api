package nz.co.mircle.v1.api.iam.controller;

import com.google.gson.Gson;
import nz.co.mircle.v1.api.iam.services.AuthenticationService;
import nz.co.mircle.v1.api.profileImage.controller.ProfileImageController;
import nz.co.mircle.v1.api.profileImage.model.ProfileImage;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {
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

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private UserService userService;

    @MockBean
    private ProfileImageService profileImageService;

    private ProfileImage profileImage;

    private User user;

    private URL url;

    @Before
    public void setup() throws MalformedURLException {
        url = new URL("http://test.com");

        profileImage = new ProfileImage(url, "", "", true);
        user = populateUser();
    }

    @Test
    public void givenInvalidUserValidateThatItDoesNotExist() throws Exception {
        when(userService.findUser(EMAIL_ADDRESS)).thenReturn(null);

        mvc.perform(get(String.format("/register/user/validate?emailAddress=%s", EMAIL_ADDRESS)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenUserValidateThatItExist() throws Exception {
        when(userService.findUser(EMAIL_ADDRESS)).thenReturn(user);

        mvc.perform(get(String.format("/register/user/validate?emailAddress=%s", EMAIL_ADDRESS)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void registerUser() throws Exception {
        doNothing().when(authenticationService).createUser(user);

        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        MvcResult result = mvc.perform(post("/register").content(jsonUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(String.valueOf(ID));
    }

    @Test
    public void givenIdAndNoProfileImageThenRegisterUserProfileImageToBeDefault() throws Exception {
        when(userService.findUser(ID)).thenReturn(user);
        when(profileImageService.getDefaultImage()).thenReturn(url);
        when(userService.setUserProfileImage(user, url)).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.fileUpload("/register/profileimage")
                .param("id", String.valueOf(ID)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenEmailAddressAndProfileImageThenRegisterUserProfileImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile("profileImage", "test.png", "application/json", "{}".getBytes());

        when(userService.findUser(EMAIL_ADDRESS)).thenReturn(user);
        when(profileImageService.uploadProfileImageToS3(file, EMAIL_ADDRESS)).thenReturn(url);
        when(userService.setUserProfileImage(user, url)).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.fileUpload("/register/profileimage")
                .file(file)
                .param("emailAddress", EMAIL_ADDRESS))
                .andExpect(status().isOk());

    }

    private User populateUser() {
        //TODO
        // Have to set LocalDateTime to null for now as there is an issue converting it to JSON, fix later.
        User user = new User(EMAIL_ADDRESS, PASSWORD, FIRST_NAME, SURNAME, GENDER, PHONE_NUMBER, null, OCCUPATION, null, null, IS_LOGGED_IN, null, null, null);
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
        sb.append(String.format("\"friends\":%s", user.getFriends()));
        sb.append(String.format("\"feeds\":%s", user.getFeeds()));
        sb.append("}");
        return sb.toString();
    }
}
