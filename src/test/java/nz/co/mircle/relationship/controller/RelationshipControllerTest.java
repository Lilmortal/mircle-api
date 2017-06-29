package nz.co.mircle.relationship.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nz.co.mircle.permission.model.Permission;
import nz.co.mircle.permission.services.PermissionService;
import nz.co.mircle.relationship.model.Relationship;
import nz.co.mircle.relationship.services.RelationshipService;
import nz.co.mircle.socialMedia.model.SocialMedia;
import nz.co.mircle.socialMedia.services.SocialMediaService;
import nz.co.mircle.user.model.User;
import nz.co.mircle.user.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/** Relationship controller test */
@RunWith(SpringRunner.class)
@WebMvcTest(RelationshipController.class)
public class RelationshipControllerTest {
  private static final Long USER_ID = Long.parseLong("1");

  private static final Long FRIEND_ID = Long.parseLong("2");

  private static final String SOCIAL_MEDIA_NAME = "Facebook";

  private static final boolean HAS_ACCESS = true;

  @Autowired private MockMvc mvc;

  @MockBean private RelationshipService relationshipService;

  @MockBean private UserService userService;

  @MockBean private SocialMediaService socialMediaService;

  @MockBean private PermissionService permissionService;

  @MockBean(name = "user")
  private User user;

  @MockBean(name = "friend")
  private User friend;

  @MockBean private SocialMedia socialMedia;

  @MockBean private Permission permission;

  @Before
  public void setup() {
    doNothing().when(relationshipService).createRelationship(any(Relationship.class));

    when(userService.findUser(USER_ID)).thenReturn(user);
    when(userService.findUser(FRIEND_ID)).thenReturn(friend);
    when(socialMediaService.findSocialMedia(SOCIAL_MEDIA_NAME)).thenReturn(socialMedia);
  }

  @Test
  public void givenUserAndFriendIdAndSocialMediaNameAndHasAccessCreateARelationship()
      throws Exception {
    mvc.perform(
            post("/relationship/create?userId="
                    + USER_ID
                    + "&friendId="
                    + FRIEND_ID
                    + "&socialMediaName="
                    + SOCIAL_MEDIA_NAME
                    + "&hasAccess="
                    + HAS_ACCESS)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }
}
