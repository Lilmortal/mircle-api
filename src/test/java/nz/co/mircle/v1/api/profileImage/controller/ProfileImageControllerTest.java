package nz.co.mircle.v1.api.profileImage.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.MalformedURLException;
import java.net.URL;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

/** Created by jacktan on 11/09/17. */
@RunWith(SpringRunner.class)
@WebMvcTest(ProfileImageController.class)
public class ProfileImageControllerTest {
  private static final Long ID = Long.valueOf("1");

  private static final String EMAIL_ADDRESS = "test@test.com";

  @Autowired private MockMvc mvc;

  @MockBean private UserService userService;

  @MockBean private ProfileImageService profileImageService;

  @Mock private MultipartFile profileImage;

  @Mock private User user;

  private URL url;

  @Before
  public void setup() throws MalformedURLException {
    url = new URL("http://test.com");

    when(user.getEmailAddress()).thenReturn(EMAIL_ADDRESS);
  }

  @Test
  public void getDefaultProfileImage() throws Exception {
    when(profileImageService.getDefaultImage()).thenReturn(url);

    MvcResult result =
        mvc.perform(get("/profileimage/default").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    assertThat(result.getResponse().getContentAsString()).isEqualTo(String.format("\"%s\"", url));
  }

  @Test
  public void givenIdThenUploadProfileImageToS3() throws Exception {
    MockMultipartFile file =
        new MockMultipartFile("profileImage", "test.png", "application/json", "{}".getBytes());

    when(userService.findUser(ID)).thenReturn(user);
    when(profileImageService.uploadProfileImageToS3(profileImage, EMAIL_ADDRESS)).thenReturn(url);

    MvcResult result =
        mvc.perform(
                MockMvcRequestBuilders.fileUpload("/profileimage/upload/s3")
                    .file(file)
                    .param("id", String.valueOf(ID)))
            .andExpect(status().isOk())
            .andReturn();

    assertThat(result.getResponse().getContentAsString()).isEqualTo(url.getPath());
  }

  @Test
  public void givenEmailAddressThenUploadProfileImageToS3() throws Exception {
    MockMultipartFile file =
        new MockMultipartFile("profileImage", "test.png", "application/json", "{}".getBytes());

    when(profileImageService.uploadProfileImageToS3(profileImage, EMAIL_ADDRESS)).thenReturn(url);

    MvcResult result =
        mvc.perform(
                MockMvcRequestBuilders.fileUpload("/profileimage/upload/s3")
                    .file(file)
                    .param("emailAddress", EMAIL_ADDRESS))
            .andExpect(status().isOk())
            .andReturn();

    assertThat(result.getResponse().getContentAsString()).isEqualTo(url.getPath());
  }
}
