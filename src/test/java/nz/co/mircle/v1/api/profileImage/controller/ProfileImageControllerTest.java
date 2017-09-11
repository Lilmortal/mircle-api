package nz.co.mircle.v1.api.profileImage.controller;

import nz.co.mircle.v1.api.profileImage.model.ProfileImage;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.user.controller.UserController;
import nz.co.mircle.v1.api.user.model.User;
import nz.co.mircle.v1.api.user.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jacktan on 11/09/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class ProfileImageControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProfileImageService profileImageService;

    @Mock
    private ProfileImage profileImage;

    @Test
    public void givenIdThenReturnUser() throws Exception {
        User user = populateUser();

        when(userService.findUser(ID)).thenReturn(user);

        MvcResult result = mvc.perform(get(String.format("/user/%d", ID)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = getJsonResult(user);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(jsonResult);
    }
}
