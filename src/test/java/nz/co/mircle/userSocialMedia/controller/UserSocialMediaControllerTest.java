package nz.co.mircle.userSocialMedia.controller;

import nz.co.mircle.permission.controller.PermissionController;
import nz.co.mircle.socialMedia.model.SocialMedia;
import nz.co.mircle.socialMedia.services.SocialMediaService;
import nz.co.mircle.user.model.User;
import nz.co.mircle.user.services.UserService;
import nz.co.mircle.userSocialMedia.model.UserSocialMedia;
import nz.co.mircle.userSocialMedia.services.UserSocialMediaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User social media controller test
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserSocialMediaController.class)
public class UserSocialMediaControllerTest {
    private static final Long USER_ID = Long.parseLong("1");

    private static final Long SOCIAL_MEDIA_ID = Long.parseLong("2");

    private static final String URL = "url";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserSocialMediaService userSocialMediaService;

    @MockBean
    private UserService userService;

    @MockBean
    private SocialMediaService socialMediaService;

    @MockBean
    private UserSocialMedia userSocialMedia;

    @MockBean
    private User user;

    @MockBean
    private SocialMedia socialMedia;

    @Before
    public void setup() {
        when(userService.findUser(USER_ID)).thenReturn(user);
        when(socialMediaService.findSocialMedia(SOCIAL_MEDIA_ID)).thenReturn(socialMedia);

        doNothing().when(userSocialMediaService).createUserSocialMedia(any(UserSocialMedia.class));
    }

    @Test
    public void givenUserIdAndSocialMediaIdAndUrlCreateAUserSocialMedia() throws Exception {
        mvc.perform(post("/social/media/user/create?userId=" + USER_ID +
                "&socialMediaId=" + SOCIAL_MEDIA_ID + "&url=" + URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }
}
