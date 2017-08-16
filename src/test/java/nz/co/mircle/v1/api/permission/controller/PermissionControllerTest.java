package nz.co.mircle.v1.api.permission.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nz.co.mircle.v1.api.permission.controller.PermissionController;
import nz.co.mircle.v1.api.permission.model.Permission;
import nz.co.mircle.v1.api.permission.services.PermissionService;
import nz.co.mircle.v1.api.socialMedia.model.SocialMedia;
import nz.co.mircle.v1.api.socialMedia.services.SocialMediaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/** Permission controller test. */
@RunWith(SpringRunner.class)
@WebMvcTest(PermissionController.class)
public class PermissionControllerTest {
  private static final Long SOCIAL_MEDIA_ID = Long.valueOf("1");

  private static final boolean HAS_ACCESS = true;

  @Autowired private MockMvc mvc;

  @MockBean private SocialMedia socialMedia;

  @MockBean private PermissionService permissionService;

  @MockBean private SocialMediaService socialMediaService;

  @MockBean private Permission permission;

  @Before
  public void setup() {
    doNothing().when(permissionService).createPermission(permission);
    when(socialMediaService.findSocialMedia(SOCIAL_MEDIA_ID)).thenReturn(socialMedia);
  }

  @Test
  public void givenSocialMediaIdAndHasAccessCreateANewPermission() throws Exception {
    mvc.perform(
            post("/permission/create?socialMediaId=" + SOCIAL_MEDIA_ID + "&hasAccess=" + HAS_ACCESS)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }
}
