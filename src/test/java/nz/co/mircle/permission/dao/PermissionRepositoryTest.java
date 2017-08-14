package nz.co.mircle.permission.dao;

import static org.assertj.core.api.Java6Assertions.assertThat;

import nz.co.mircle.v1.api.permission.dao.PermissionRepository;
import nz.co.mircle.v1.api.permission.model.Permission;
import nz.co.mircle.v1.api.socialMedia.model.SocialMedia;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/** Permission repository test */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PermissionRepositoryTest {
  private static final String SOCIAL_MEDIA_NAME = "facebook";

  private static final String SOCIAL_MEDIA_LOGO = "facebook_logo";

  private static final boolean HAS_ACCESS = true;

  @Autowired private TestEntityManager entityManager;

  @Autowired private PermissionRepository permissionRepository;

  @Test
  public void givenSocialMediaAndHasAccessReturnPermission() {
    SocialMedia socialMedia = new SocialMedia(SOCIAL_MEDIA_NAME, SOCIAL_MEDIA_LOGO);
    Permission permission = new Permission(socialMedia, HAS_ACCESS);
    entityManager.persist(permission);
    entityManager.flush();

    Permission result = permissionRepository.findBySocialMediaAndHasAccess(socialMedia, HAS_ACCESS);

    assertThat(result.getSocialMedia()).isEqualTo(socialMedia);
    assertThat(result.isHasAccess()).isEqualTo(HAS_ACCESS);
  }
}
