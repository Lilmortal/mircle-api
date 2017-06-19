package nz.co.mircle.socialMedia.controller;

import com.google.gson.Gson;
import nz.co.mircle.relationship.controller.RelationshipController;
import nz.co.mircle.socialMedia.model.SocialMedia;
import nz.co.mircle.socialMedia.services.SocialMediaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Social media controller test
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SocialMediaController.class)
public class SocialMediaControllerTest {
    private static final String SOCIAL_MEDIA_NAME = "facebook";

    private static final String SOCIAL_NAME_LOGO = "facebook_logo";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SocialMediaService socialMediaService;

    @Test
    public void createSocialMedia() throws Exception {
        SocialMedia socialMedia = new SocialMedia(SOCIAL_MEDIA_NAME, SOCIAL_NAME_LOGO);
        doNothing().when(socialMediaService).createSocialMedia(socialMedia);

        Gson gson = new Gson();
        String json = gson.toJson(socialMedia);
        mvc.perform(post("/social/media/create").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }
}
